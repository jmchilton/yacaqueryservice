package com.github.yacaqueryservice.client;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.yacaqueryservice.BaseQueryResults;
import com.github.yacaqueryservice.ListQueryResults;
import com.github.yacaqueryservice.ListResult;
import com.github.yacaqueryservice.ObjectQueryResults;
import com.github.yacaqueryservice.QueryWebServiceBase;
import com.github.yacaqueryservice.ql.BaseQuery;
import com.github.yacaqueryservice.ql.ListQuery;
import com.github.yacaqueryservice.ql.ObjectQuery;
import com.google.common.collect.Lists;

public class QueryServiceImplUnitTest {
  private static final String EXPECTED_SERVICE_URI = UUID.randomUUID().toString();

  private <T extends QueryWebServiceBase> QueryServiceImpl<T> getQueryService(final T webService) {
    final QueryServiceImpl<T> service = new QueryServiceImpl<T>();
    service.setWebServiceClientFactory(new TestWebServiceFactory<T>(EXPECTED_SERVICE_URI, webService));
    return service;
  }
  
  static class TestWebServiceFactory<T extends QueryWebServiceBase> implements WebServiceClientFactory<T> {
    private String expectedServiceUri;
    private T queryWebService;
    
    TestWebServiceFactory(final String expectedServiceUri, final T queryWebService) {
      this.expectedServiceUri = expectedServiceUri;
      this.queryWebService = queryWebService;
    }
    
    public T getClient(final String serviceUri) {
      Assert.assertEquals(expectedServiceUri, serviceUri);
      return queryWebService;
    }
    
  }
  
  abstract class TestQueryWebServiceBase<T extends BaseQueryResults> implements QueryWebServiceBase {
    private String queryId = null;

    public ObjectQueryResults setupQuery(final ObjectQuery query) {
      throw new UnsupportedOperationException();
    }

    public ListQueryResults setupListQuery(final ListQuery query) {
      throw new UnsupportedOperationException();
    }

    public ObjectQueryResults nextBatch(final String queryId) {
      throw new UnsupportedOperationException();
    }

    public ListQueryResults nextListBatch(final String queryId) {
      throw new UnsupportedOperationException();
    }

    protected T setQueryIdAndReturnNextBatch() {
      queryId = UUID.randomUUID().toString();
      return nextBatch();
    }
    
    protected T verifyQueryIdAndReturnNextBatch(final String queryId) {
      Assert.assertNotNull(this.getQueryId());
      Assert.assertEquals(this.getQueryId(), queryId);
      return nextBatch();
    }
    
    protected abstract T nextBatch();

    public String getQueryId() {
      return queryId;
    }

  }

  class TestListQueryWebService extends TestQueryWebServiceBase<ListQueryResults> {
    private final Iterator<? extends Iterable<ListResult>> results;
        
    TestListQueryWebService(final Iterable<? extends Iterable<ListResult>> results) {
      this.results = results.iterator();
    }

    protected ListQueryResults nextBatch() {
      final ListQueryResults results = new ListQueryResults();
      results.setResults(Lists.newArrayList(this.results.next()));
      results.setQueryId(getQueryId());
      results.setMoreResults(this.results.hasNext());
      return results;
    }

    @Override
    public ListQueryResults setupListQuery(final ListQuery objectQuery) {
      return setQueryIdAndReturnNextBatch();
    }
    
    @Override
    public ListQueryResults nextListBatch(final String queryId) {
      return verifyQueryIdAndReturnNextBatch(queryId); 
    }


  }
  
  class TestObjectQueryWebService extends TestQueryWebServiceBase<ObjectQueryResults> {
    private final Iterator<? extends Iterable<Object>> results;
    
    TestObjectQueryWebService(final Iterable<? extends Iterable<Object>> results) {
      this.results = results.iterator();
    }

    @Override
    public ObjectQueryResults setupQuery(final ObjectQuery objectQuery) {
      return setQueryIdAndReturnNextBatch();
    }
    
    @Override
    public ObjectQueryResults nextBatch(final String queryId) {
      return verifyQueryIdAndReturnNextBatch(queryId); 
    }

    @Override
    protected ObjectQueryResults nextBatch() {  
      final ObjectQueryResults results = new ObjectQueryResults();
      results.setResults(Lists.newArrayList(this.results.next()));
      results.setQueryId(getQueryId());
      results.setMoreResults(this.results.hasNext());
      return results;
    }

  }
  
  private QueryService queryService;
  private List<List<Object>> expectedObjectResults;
  private List<List<ListResult>> expectedListResults;
  
  
  @Before
  public void init() {
    expectedObjectResults = Lists.newArrayList();
    expectedListResults = Lists.newArrayList();
    registerNewBatch();
  }
  
  private void registerResult(final Object result) {
    lastList(expectedObjectResults).add(result);
  }
  
  private <T> List<T> lastList(final List<List<T>> outerList) {
    return outerList.get(outerList.size() - 1);
  }
  
  private ListResult registerListResult(final Object... objects) {
    final ListResult listResult = new ListResult(Lists.newArrayList(objects));
    lastList(expectedListResults).add(listResult);
    return listResult;
  }
  
  private void registerNewBatch() {
    expectedObjectResults.add(Lists.newArrayList());
    expectedListResults.add(Lists.<ListResult>newArrayList());
    
  }
  
  private <T extends BaseQueryResults> void setupQueryForBackend(final TestQueryWebServiceBase<T> backend) {
    queryService = getQueryService(backend);    
  }
  
  private <T extends BaseQuery> AddressedQuery<T> getAddressedQuery(final T query) {    
    final AddressedQuery<T> addressedQuery = new AddressedQuery<T>();
    addressedQuery.setServiceUri(EXPECTED_SERVICE_URI);
    addressedQuery.setQuery(query);
    return addressedQuery;
  }
  
  @Test
  public void testSetupObjects() {
    registerResult(7);
    final Iterator<Object> resultIterator = getObjectResultsForExpectedObjects();
    
    Assert.assertTrue(resultIterator.hasNext());
    Assert.assertEquals(7, resultIterator.next());
    Assert.assertFalse(resultIterator.hasNext());
  }
  
  @Test
  public void testMultipleObjectBatches() {
    registerResult(7);
    registerResult(8);
    registerNewBatch();
    registerResult(9);
    final Iterator<Object> resultIterator = getObjectResultsForExpectedObjects();
    
    Assert.assertEquals(7, resultIterator.next());
    Assert.assertEquals(8, resultIterator.next());
    Assert.assertEquals(9, resultIterator.next());
    Assert.assertFalse(resultIterator.hasNext());
  }
  
  @Test
  public void testSetupLists() {
    final ListResult result1 = registerListResult(7, 8);
    final Iterator<ListResult> resultIterator = getListResultsForExpectedLists();
    Assert.assertEquals(result1, resultIterator.next());
    Assert.assertFalse(resultIterator.hasNext());
  }
  
  @Test
  public void testMultipleListBatches() {
    final ListResult result1 = registerListResult(7, 8, 9);
    registerNewBatch();    
    final ListResult result2 = registerListResult(1, 2, 3);
    final Iterator<ListResult> resultIterator = getListResultsForExpectedLists();
    Assert.assertEquals(result1, resultIterator.next());
    Assert.assertEquals(result2, resultIterator.next());
    Assert.assertFalse(resultIterator.hasNext());
  }
  
  @Test
  public void testEmptyList() {
    final Iterator<ListResult> resultIterator = getListResultsForExpectedLists();
    Assert.assertFalse(resultIterator.hasNext());
  }

  @Test
  public void testEmptyObjects() {
    final Iterator<Object> resultIterator = getObjectResultsForExpectedObjects();
    Assert.assertFalse(resultIterator.hasNext());
  }

  private Iterator<Object> getObjectResultsForExpectedObjects() {
    final TestObjectQueryWebService backend = new TestObjectQueryWebService(expectedObjectResults);
    setupQueryForBackend(backend);
    final AddressedQuery<ObjectQuery> addressedQuery = getAddressedQuery(new ObjectQuery());
    
    final Iterator<Object> resultIterator = queryService.query(addressedQuery);
    return resultIterator;
  }
  
  private Iterator<ListResult> getListResultsForExpectedLists() {
    final TestListQueryWebService backend = new TestListQueryWebService(expectedListResults);
    setupQueryForBackend(backend);
    final AddressedQuery<ListQuery> addressedQuery = getAddressedQuery(new ListQuery());
    
    final Iterator<ListResult> resultIterator = queryService.queryForLists(addressedQuery);
    return resultIterator;
  }

}
