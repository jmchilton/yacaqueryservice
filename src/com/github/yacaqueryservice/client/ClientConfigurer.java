package com.github.yacaqueryservice.client;

import com.github.yacaqueryservice.QueryWebServiceBase;

/**
 * A service client factory can be configured with an instance of this interface, which gives the consumer of the client an opportunity to configure
 * the CXF client before it is returned from factory.
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 * 
 * @param <T> Type of the interface defining the underlying yacaquery service.
 * 
 */
public interface ClientConfigurer<T extends QueryWebServiceBase> {

  void configureClient(final T client);

}
