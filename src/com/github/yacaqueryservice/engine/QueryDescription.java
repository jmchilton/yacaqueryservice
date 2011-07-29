package com.github.yacaqueryservice.engine;

import java.util.List;

/**
 * This bean class describes a query to be executed by the query engine.
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 * 
 */
public class QueryDescription {
  /**
   * Actual HQL of the query.
   */
  private String hql;
  
  /**
   * Constant values corresponding to query parameters found in {@link hql}.
   */
  private List<Object> parameters;
  
  /**
   * A description of those associations between beans to be eagerly expanded after the query has been execute.
   */
  private Iterable<String> eagerAssociations;
  
  /**
   * An (optional) distinct id assigned to the query to track it through its life cycle.
   */
  private String queryId;

  public String getHql() {
    return hql;
  }

  public List<Object> getParameters() {
    return parameters;
  }

  public Iterable<String> getEagerAssociations() {
    return eagerAssociations;
  }

  public String getQueryId() {
    return queryId;
  }

  public void setQueryId(final String queryId) {
    this.queryId = queryId;
  }

  public void setHql(final String hql) {
    this.hql = hql;
  }

  public void setParameters(final List<Object> parameters) {
    this.parameters = parameters;
  }

  public void setEagerAssociations(final Iterable<String> eagerAssociations) {
    this.eagerAssociations = eagerAssociations;
  }

}