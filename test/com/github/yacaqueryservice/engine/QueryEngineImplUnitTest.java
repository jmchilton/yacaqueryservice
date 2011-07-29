package com.github.yacaqueryservice.engine;

import gov.nih.nci.system.applicationservice.ApplicationException;
import gov.nih.nci.system.query.hibernate.HQLCriteria;

import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class QueryEngineImplUnitTest {

  private QueryEngineImpl queryEngine;
  private MockHibernateWrapper applicationService;
  private MockBeanSanitizer beanSanitizer;

  static class ExampleBean implements Cloneable {
    private boolean sanitized = false;
    
    public ExampleBean clone() {
      try {
        return (ExampleBean) super.clone();
      } catch(final CloneNotSupportedException e) {
        throw new IllegalStateException(e);
      }
    }
    
  }
  
  static class MockBeanSanitizer implements BeanSanitizer {

    @SuppressWarnings("unchecked")
    public <T> T sanitize(final T hibernateObject, final Iterable<String> eagerAssociations) {
      final T sanitizedObject;
      if(hibernateObject instanceof ExampleBean) {
        ExampleBean copy = ((ExampleBean) hibernateObject).clone();
        copy.sanitized = true;
        sanitizedObject = (T) copy;
      } else {
        sanitizedObject = hibernateObject;
      }
      return sanitizedObject;
    }
    
  }
  
  static class MockHibernateWrapper implements HibernateWrapper {
    private List<?> results; 
    private HQLCriteria lastCriteria;

    public <T> List<T> query(final HQLCriteria criteria) throws ApplicationException {
      this.lastCriteria = criteria;
      return (List<T>) results;
    }
    
  }
  
  @Before
  public void init() {
    applicationService = new MockHibernateWrapper();
    beanSanitizer = new MockBeanSanitizer();
    queryEngine = new QueryEngineImpl(applicationService, beanSanitizer);
  }
  
  @Test
  public void testObjectResults() {
    applicationService.results = Lists.newArrayList(7);
    final QueryDescription description = new QueryDescription();
    description.setHql("from Unit u");
    final Iterator<Integer> results = queryEngine.<Integer>query(description);
    Assert.assertEquals(Integer.valueOf(7), results.next());
  }
  
  @Test
  public void testListResults() {
    applicationService.results = Lists.newArrayList(new Object[] {7, 1}, new Object[] {8, 2});
    final QueryDescription description = new QueryDescription();
    description.setHql("from Unit u");
    final Iterator<List<Object>> results = queryEngine.queryForLists(description);
    final List<Object> firstList = (List<Object>) results.next();
    final List<Object> secondList = (List<Object>) results.next();
    Assert.assertEquals(Integer.valueOf(7), firstList.get(0));
    Assert.assertEquals(Integer.valueOf(8), secondList.get(0));
  }
  
  @Test
  public void testSingletonListResults() {
    applicationService.results = Lists.newArrayList(7, 8);
    final QueryDescription description = new QueryDescription();
    description.setHql("from Unit u");
    final Iterator<List<Object>> results = queryEngine.queryForLists(description);
    final List<Object> firstList = (List<Object>) results.next();
    final List<Object> secondList = (List<Object>) results.next();
    Assert.assertEquals(Integer.valueOf(7), firstList.get(0));
    Assert.assertEquals(Integer.valueOf(8), secondList.get(0));    
  }
  
}
