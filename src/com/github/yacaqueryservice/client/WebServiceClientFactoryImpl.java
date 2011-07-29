package com.github.yacaqueryservice.client;


import org.springframework.beans.factory.annotation.Autowired;

import com.github.yacaqueryservice.QueryWebServiceBase;

public class WebServiceClientFactoryImpl<T extends QueryWebServiceBase> implements WebServiceClientFactory<T> {
  private final Class<T> queryWebServiceClass;
  private ClientConfigurer<T> clientConfigurer = new NullClientConfigurer<T>();

  public WebServiceClientFactoryImpl(final Class<T> queryWebServiceClass) {
      this.queryWebServiceClass = queryWebServiceClass;
  }

  public T getClient(final String serviceUri) {
    org.apache.cxf.jaxws.JaxWsProxyFactoryBean clientFactory = new org.apache.cxf.jaxws.JaxWsProxyFactoryBean();
    clientFactory.setServiceClass(queryWebServiceClass);
    clientFactory.setAddress(serviceUri);    
    @SuppressWarnings("unchecked")
    T serviceClient = (T) clientFactory.create();
    clientConfigurer.configureClient(serviceClient);
    return serviceClient;
  }

  @Autowired(required = false)
  public void setClientConfigurer(final ClientConfigurer<T> clientConfigurer) {
    this.clientConfigurer = clientConfigurer;
  }
  
}