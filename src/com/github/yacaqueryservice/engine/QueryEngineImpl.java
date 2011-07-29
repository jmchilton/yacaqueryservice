package com.github.yacaqueryservice.engine;

import gov.nih.nci.system.applicationservice.ApplicationException;
import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.query.hibernate.HQLCriteria;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

/**
 * Implementation of {@link QueryEngine} wrapping an existing {@link ApplicationService}.
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 * 
 */
@Component
public class QueryEngineImpl implements QueryEngine {
  private static final Log LOG = LogFactory.getLog(QueryEngineImpl.class);
  private final HibernateWrapper applicationService;
  private final BeanSanitizer beanSanitizer;
  
  private static class ApplicationServiceWrapper implements HibernateWrapper {
    private final ApplicationService applicationService;
    
    public ApplicationServiceWrapper(final ApplicationService applicationService) {
      this.applicationService = applicationService;
    }


    public <T> List<T> query(final HQLCriteria criteria) throws ApplicationException {
      return applicationService.query(criteria);
    }
    
  }

  @Autowired
  public QueryEngineImpl(final ApplicationService applicationService) {
    this(new ApplicationServiceWrapper(applicationService), new BeanSanitizerImpl());
  }
  
  @VisibleForTesting
  QueryEngineImpl(final HibernateWrapper applicationService,
                  final BeanSanitizer beanSanitizer) {
    this.applicationService = applicationService;
    this.beanSanitizer = beanSanitizer;
  }

  private <T> List<T> applicationServiceQuery(final HQLCriteria criteria) {
    final List<T> hibernateObjects;
    try {
      LOG.debug("Executing query against applicationService");
      hibernateObjects = applicationService.<T>query(criteria);
      LOG.debug("Application service returned");
    } catch(ApplicationException e) {
      LOG.debug("applicationService threw exception", e);
      throw new RuntimeException(e);
    }
    return hibernateObjects;
  }

  private abstract class AbstractQueryContext<T, R> {
    private final Iterable<String> eagerAssociations;
    private final HQLCriteria criteria;

    AbstractQueryContext(final QueryDescription query) {
      final HQLCriteria criteria = new HQLCriteria(query.getHql(), query.getParameters());
      this.eagerAssociations = query.getEagerAssociations();
      this.criteria = criteria;
    }

    abstract R processResult(T object);

    Iterator<R> query() {
      final List<T> hibernateResults = applicationServiceQuery(criteria);
      return Iterators.transform(hibernateResults.iterator(), new Function<T, R>() {
        public R apply(final T hibernateResult) {
          LOG.debug("sanitizing result");
          final R sanitizedObject = processResult(hibernateResult);
          LOG.debug("result sanitized");
          return sanitizedObject;
        }
      });
    }

    public Iterable<String> getEagerAssociations() {
      return eagerAssociations;
    }
  }

  private class ObjectQueryContext<T> extends AbstractQueryContext<T, T> {

    ObjectQueryContext(final QueryDescription queryDescription) {
      super(queryDescription);
    }

    T processResult(final Object object) {
      @SuppressWarnings("unchecked")
      final T sanitizedObject = (T) beanSanitizer.sanitize(object, getEagerAssociations());
      return sanitizedObject;
    }

  }

  private class ArrayQueryContext extends AbstractQueryContext<Object, List<Object>> {

    ArrayQueryContext(final QueryDescription queryDescription) {
      super(queryDescription);
    }

    List<Object> processResult(final Object objects) {
      int length = !objects.getClass().isArray() ? 1 : ((Object[]) objects).length;
      List<Object> sanitizedList = Lists.newArrayListWithCapacity(length);
      if(objects.getClass().isArray()) {
        for(Object object : ((Object[]) objects)) {
          sanitizedList.add(beanSanitizer.sanitize(object, getEagerAssociations()));
        }
      } else {
        sanitizedList.add(beanSanitizer.sanitize(objects, getEagerAssociations()));
      }
      return sanitizedList;
    }
  }

  public <T> Iterator<T> query(final QueryDescription query) {
    return new ObjectQueryContext<T>(query).query();
  }

  public Iterator<List<Object>> queryForLists(final QueryDescription query) {
    return new ArrayQueryContext(query).query();
  }

}
