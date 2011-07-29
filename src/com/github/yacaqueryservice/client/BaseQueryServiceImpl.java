package com.github.yacaqueryservice.client;

import java.util.Iterator;

import com.github.yacaqueryservice.BaseQueryResults;
import com.github.yacaqueryservice.ListQueryResults;
import com.github.yacaqueryservice.ListResult;
import com.github.yacaqueryservice.ObjectQueryResults;
import com.github.yacaqueryservice.QueryWebServiceBase;
import com.github.yacaqueryservice.ql.ListQuery;
import com.github.yacaqueryservice.ql.ObjectQuery;

/**
 * This class contains code shared between the web service and local
 * implementations of {@link QueryService}. 
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
public abstract class BaseQueryServiceImpl implements QueryService {
  private ClientQueryListener clientQueryListener; 
  
  protected BaseQueryServiceImpl(final ClientQueryListener clientQueryListener) {
    this.clientQueryListener = clientQueryListener;
  }
  
  protected BaseQueryServiceImpl() {
    this(new NullQueryClientListenerImpl());
  }
  
  protected abstract QueryWebServiceBase getQueryClient(final String serviceUri);

  private abstract class QueryIterator<R extends BaseQueryResults, T> extends com.google.common.collect.AbstractIterator<T> {
    private final String queryId;
    private final String serviceUri;
    private boolean lastIterator = false;
    private Iterator<T> objectIterator = null;

    QueryIterator(final R queryResults, final String serviceUri) {
      this.queryId = queryResults.getQueryId();
      this.serviceUri = serviceUri;
      setResults(queryResults);
    }

    private void setResults(final R results) {
      clientQueryListener.onQueryReturned(queryId, results);
      this.lastIterator = !results.isMoreResults();
      this.objectIterator = asIterator(results);
    }

    protected abstract R nextBatch(final String serviceUri, final String queryId);

    protected abstract Iterator<T> asIterator(final R results);

    private void initObjectIterator() {
      while(!objectIterator.hasNext() && !lastIterator) {
        final R results = nextBatch(serviceUri, queryId);
        setResults(results);
      }
    }

    private boolean atEndOfData() {
      return objectIterator == null || (!objectIterator.hasNext() && lastIterator);
    }

    protected T computeNext() {
      initObjectIterator();
      if(atEndOfData()) {
        return endOfData();
      }
      return objectIterator.next();
    }

  }

  private class ObjectQueryIterator<T> extends QueryIterator<ObjectQueryResults, T> {

    ObjectQueryIterator(final ObjectQueryResults results, final String serviceUri) {
      super(results, serviceUri);
    }

    protected ObjectQueryResults nextBatch(final String serviceUri, final String queryId) {
      final ObjectQueryResults results = getQueryClient(serviceUri).nextBatch(queryId);
      return results;
    }

    protected Iterator<T> asIterator(final ObjectQueryResults results) {
      @SuppressWarnings("unchecked")
      final Iterator<T> iterator = (Iterator<T>) results.getResults().iterator();
      return iterator;
    }

  }

  private class ListQueryIterator extends QueryIterator<ListQueryResults, ListResult> {

    ListQueryIterator(final ListQueryResults results, final String serviceUri) {
      super(results, serviceUri);
    }

    protected ListQueryResults nextBatch(final String serviceUri, final String queryId) {
      return getQueryClient(serviceUri).nextListBatch(queryId);
    }

    protected Iterator<ListResult> asIterator(final ListQueryResults results) {
      return results.getResults().iterator();
    }

  }

  public <T> Iterator<T> query(final AddressedQuery<ObjectQuery> query) {
    clientQueryListener.onQueryIssued(query);
    final ObjectQueryResults results = getQueryClient(query.getServiceUri()).setupQuery(query.getQuery());
    return new ObjectQueryIterator<T>(results, query.getServiceUri());
  }

  public Iterator<ListResult> queryForLists(final AddressedQuery<ListQuery> query) {
    clientQueryListener.onQueryIssued(query);
    final ListQueryResults results = getQueryClient(query.getServiceUri()).setupListQuery(query.getQuery());
    return new ListQueryIterator(results, query.getServiceUri());
  }

  public void setClientQueryListener(final ClientQueryListener clientQueryListener) {
    this.clientQueryListener = clientQueryListener;
  }

}