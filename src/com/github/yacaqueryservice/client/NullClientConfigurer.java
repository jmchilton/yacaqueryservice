package com.github.yacaqueryservice.client;

import com.github.yacaqueryservice.QueryWebServiceBase;

/**
 * A no-op implementation of {@link ClientConfigurer}.
 * @author John Chilton (jmchilton at gmail dot com)
 *
 * @param <T> Type of the interface defining the underlying yacaquery service.
 */
public class NullClientConfigurer<T extends QueryWebServiceBase> implements ClientConfigurer<T> {

  public void configureClient(final T client) {    
  }

}
