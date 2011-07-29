package com.github.yacaqueryservice;

import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.github.yacaqueryservice.engine.QueryDescription;
import com.github.yacaqueryservice.engine.QueryEngine;
import com.github.yacaqueryservice.example.Unit;
import com.github.yacaqueryservice.ql.BaseQuery;
import com.github.yacaqueryservice.ql.ListQuery;
import com.github.yacaqueryservice.ql.ObjectQuery;
import com.github.yacaqueryservice.ql.Projection;
import com.github.yacaqueryservice.ql.Projections;
import com.github.yacaqueryservice.ql.Target;
import com.google.common.collect.Lists;

public class QueryWebServiceImplUnitTest {

  private class TestQueryEngine implements QueryEngine {
    private Iterator<Object> objectIterator;
    private Iterator<List<Object>> listIterator;
    private QueryDescription lastQueryDescription;
    
    public <T> Iterator<T> query(final QueryDescription queryDescription) {
      this.lastQueryDescription = queryDescription;
      @SuppressWarnings("unchecked")
      final Iterator<T> genericIterator = (Iterator<T>) objectIterator;
      return genericIterator;
    }

    public Iterator<List<Object>> queryForLists(final QueryDescription queryDescription) {
      this.lastQueryDescription = queryDescription;
      return listIterator;
    }    
    
  }
  
  private TestQueryEngine queryEngine;
  private int batchSize;
  private QueryWebServiceImpl webServiceImplementation;
  
  @Before
  public void init() {
    queryEngine = new TestQueryEngine();
    batchSize = 1000;
    webServiceImplementation = null;
  }
  
  
  private void setupQueryWebServiceImpl() {
    webServiceImplementation = new QueryWebServiceImpl();
    webServiceImplementation.setQueryEngine(queryEngine);
    webServiceImplementation.setBatchSize(batchSize);
  }
  
  private void setObjectIterator(final Object... objects) {
    queryEngine.objectIterator = Lists.<Object>newArrayList(objects).iterator();
  }
  
  private void setListIterator(final List<Object>... lists) {
    queryEngine.listIterator = Lists.newArrayList(lists).iterator();
  }
  
  @Test
  public void testSetupObjectQuery() {
    setObjectIterator(1, 2, 4);
    final ObjectQueryResults results = setupSimpleObjectQueryAndVerifyQueryDescription();
    Assert.assertEquals(1, results.getResults().get(0));
    Assert.assertEquals(2, results.getResults().get(1));
    Assert.assertEquals(4, results.getResults().get(2));
  }


  private ObjectQuery getSimpleObjectQuery() {
    final Target target = new Target(Unit.class);
    final ObjectQuery objectQuery = new ObjectQuery(target);
    return objectQuery;
  }
  
  @Test
  public void testObjectBatching() {
    setObjectIterator(1, 2, 3);
    batchSize = 2;
    final ObjectQueryResults results = setupSimpleObjectQueryAndVerifyQueryDescription();
    Assert.assertEquals(2, results.getResults().size());
  }
  
  @Test
  public void testObjectNextBatch() {
    setObjectIterator(1, 2, 3);
    batchSize = 2;
    final ObjectQueryResults results = setupSimpleObjectQueryAndVerifyQueryDescription();
    final ObjectQueryResults nextResults = webServiceImplementation.nextBatch(results.getQueryId());
    Assert.assertEquals(1, nextResults.getResults().size());
    Assert.assertEquals(3, nextResults.getResults().get(0));
  }


  private ObjectQueryResults setupSimpleObjectQueryAndVerifyQueryDescription() {
    final ObjectQuery objectQuery = getSimpleObjectQuery();
    final ObjectQueryResults results = setupObjectQueryAndVerifyQueryDescription(objectQuery);
    return results;
  }
  
  @Test
  public void testSetupListQuery() {
    setListIterator(Lists.<Object>newArrayList(1, 2), Lists.<Object>newArrayList(2, 4));
    final Target target = new Target(Unit.class);
    final ListQuery listQuery = new ListQuery(target, Lists.<Projection>newArrayList(Projections.rowCount()));
    final ListQueryResults results = setupListQueryAndVerifyQueryDescription(listQuery);
    Assert.assertEquals(1, results.getResults().get(0).getResults().get(0));
    Assert.assertEquals(2, results.getResults().get(0).getResults().get(1));
    Assert.assertEquals(4, results.getResults().get(1).getResults().get(1));
  }
  
  private ListQueryResults setupListQueryAndVerifyQueryDescription(final ListQuery listQuery) {
    setupQueryWebServiceImpl();
    final ListQueryResults results = webServiceImplementation.setupListQuery(listQuery);
    verifyQueryId(results);
    verifyHql(listQuery);
    return results;    
  }

  private ObjectQueryResults setupObjectQueryAndVerifyQueryDescription(final ObjectQuery objectQuery) {
    setupQueryWebServiceImpl();
    final ObjectQueryResults results = webServiceImplementation.setupQuery(objectQuery);
    verifyQueryId(results);
    verifyHql(objectQuery);
    return results;
  }

  private void verifyHql(final BaseQuery objectQuery) {
    Assert.assertEquals(queryEngine.lastQueryDescription.getHql(), objectQuery.getHql());
  }

  private void verifyQueryId(final BaseQueryResults results) {
    Assert.assertEquals(queryEngine.lastQueryDescription.getQueryId(), results.getQueryId());
  }
  
}
