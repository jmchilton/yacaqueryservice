package com.github.yacaqueryservice.client;

import com.github.yacaqueryservice.ql.BaseQuery;

/**
 * A class meant to capture both a query and the URI to 
 * execute that query against. Implementations of {@link QueryService}
 * consume instances of this class and yield {@link java.util.Iterator}s 
 * over the results.
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 * @param <T> Type of BaseQuery.
 */
public class AddressedQuery<T extends BaseQuery> {
  private T query;
  private String serviceUri;

  public AddressedQuery() {
  }

  public AddressedQuery(final T query, final String serviceUri) {
    this.query = query;
    this.serviceUri = serviceUri;
  }

  public T getQuery() {
    return query;
  }

  public void setQuery(final T query) {
    this.query = query;
  }

  public String getServiceUri() {
    return serviceUri;
  }

  public void setServiceUri(final String serviceUri) {
    this.serviceUri = serviceUri;
  }

}
