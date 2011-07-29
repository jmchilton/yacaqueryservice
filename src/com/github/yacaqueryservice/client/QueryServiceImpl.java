package com.github.yacaqueryservice.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.yacaqueryservice.QueryWebServiceBase;
import com.google.common.base.Preconditions;

@Component
public class QueryServiceImpl<T extends QueryWebServiceBase> extends BaseQueryServiceImpl {
  private WebServiceClientFactory<T> webServiceClientFactory = null;

  public QueryServiceImpl() {
  }
  
  @Override
  protected QueryWebServiceBase getQueryClient(final String serviceUri) {
    Preconditions.checkNotNull(webServiceClientFactory);
    return webServiceClientFactory.getClient(serviceUri);
  }

  @Autowired
  public void setWebServiceClientFactory(final WebServiceClientFactory<T> webServiceClientFactory) {
    this.webServiceClientFactory = webServiceClientFactory;
  }

}
