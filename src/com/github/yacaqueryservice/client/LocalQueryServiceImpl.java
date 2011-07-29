package com.github.yacaqueryservice.client;

import com.github.yacaqueryservice.QueryWebServiceBase;

/**
 * Most of the time {@link QueryServiceImpl} will be the implementation
 * of {@link QueryService} to utilize, but this class provides an
 * implementation targeting a local database connection 
 * instead of utilizing web services. This is useful for locally testing
 * components utilizing {@link QueryService}.
 *
 * @author John Chilton (jmchilton at gmail dot com)
 *
 * @param <T> Type of the interface defining the underlying yacaquery service.
 */
public class LocalQueryServiceImpl<T extends QueryWebServiceBase> extends BaseQueryServiceImpl {
  private T localQueryService;
  
  public LocalQueryServiceImpl(final T localQueryService) {
    this.localQueryService = localQueryService;
  }
  
  protected T getQueryClient(final String serviceUri) {
    return localQueryService;
  }

}
