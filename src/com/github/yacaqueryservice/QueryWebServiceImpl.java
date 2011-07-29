package com.github.yacaqueryservice;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.yacaqueryservice.engine.QueryDescription;
import com.github.yacaqueryservice.engine.QueryEngine;
import com.github.yacaqueryservice.ql.BaseQuery;
import com.github.yacaqueryservice.ql.ListQuery;
import com.github.yacaqueryservice.ql.ObjectQuery;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;

/**
 * Base class implementing yacaquery service backends. To create a
 * concrete backend, this class should be extended to implement a child 
 * interface of {@link QueryWebServiceBase}. CXF should then be utiliized
 * to create a web service from this interface. See example in test cases.
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
public class QueryWebServiceImpl implements QueryWebServiceBase {
  private static final Log LOG = LogFactory.getLog(QueryWebServiceImpl.class);
  private int batchSize = 1000;
  // Need queries to survive multiple web service calls, this map will hold results
  // and eventually expire them.
  private ConcurrentMap<String, Iterator<?>> queryMap = new MapMaker().expiration(30 * 60, TimeUnit.SECONDS).makeMap();
  private QueryListener queryListener;
  
  private QueryEngine queryService;
  
  @Autowired
  public void setQueryEngine(final QueryEngine queryEngine) {
    this.queryService = queryEngine;
  }

  public QueryWebServiceImpl() {
    this(new NullQueryListener());
  }
  
  public QueryWebServiceImpl(final QueryListener queryListener) {
    this.queryListener = queryListener;
  }

  private QueryDescription setupQueryDescription(final BaseQuery query) {
    final String queryId = UUID.randomUUID().toString();
    queryListener.onQueryReceived(queryId, query);
    final QueryDescription queryDescription = new QueryDescription();
    queryDescription.setHql(query.getHql());
    queryDescription.setParameters(query.getConstantValues());
    queryDescription.setEagerAssociations(query.getEagerAssociations());
    queryDescription.setQueryId(queryId);
    return queryDescription;
  }

  public ObjectQueryResults setupQuery(final ObjectQuery query) {
    final QueryDescription queryDescription = setupQueryDescription(query);
    final String queryId = queryDescription.getQueryId();
    LOG.info("Executing query");
    final Iterator<Object> results = queryService.query(queryDescription);
    LOG.info("Obtained iterator - hasNext() : " + results.hasNext());
    queryMap.put(queryId, results);
    return nextBatch(queryId);
  }

  public ListQueryResults setupListQuery(final ListQuery query) {
    final QueryDescription queryDescription = setupQueryDescription(query);
    final String queryId = queryDescription.getQueryId();
    LOG.info("Executing query");
    final Iterator<List<Object>> results = queryService.queryForLists(queryDescription);
    LOG.info("Obtained iterator - hasNext() : " + results.hasNext());
    queryMap.put(queryId, results);
    return nextListBatch(queryId);
  }

  public ObjectQueryResults nextBatch(final String queryId) {
    @SuppressWarnings("unchecked")
    final Iterator<Object> objectIterator = (Iterator<Object>) queryMap.get(queryId);
    int countThisBatch = 0;

    final List<Object> objectList = Lists.newArrayList();
    while(objectIterator.hasNext() && countThisBatch++ < batchSize) {
      objectList.add(objectIterator.next());
    }
    LOG.debug("Count this batch : " + countThisBatch);
    final ObjectQueryResults queryResults = new ObjectQueryResults();
    queryResults.setResults(objectList);
    queryResults.setQueryId(queryId);
    queryResults.setMoreResults(objectIterator.hasNext());
    return queryResults;
  }

  public ListQueryResults nextListBatch(final String queryId) {
    @SuppressWarnings("unchecked")
    final Iterator<List<Object>> objectIterator = (Iterator<List<Object>>) queryMap.get(queryId);
    final List<ListResult> objectList = Lists.newArrayList();

    int countThisBatch = 0;
    while(objectIterator.hasNext() && countThisBatch++ < batchSize) {
      objectList.add(new ListResult(objectIterator.next()));
    }

    final ListQueryResults queryResults = new ListQueryResults();
    queryResults.setResults(objectList);
    queryResults.setQueryId(queryId);
    queryResults.setMoreResults(objectIterator.hasNext());
    return queryResults;
  }
  
  public void setBatchSize(final int batchSize) {
    this.batchSize = batchSize;
  }

}
