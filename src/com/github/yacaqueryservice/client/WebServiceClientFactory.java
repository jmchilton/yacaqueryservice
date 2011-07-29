package com.github.yacaqueryservice.client;

import com.github.yacaqueryservice.QueryWebServiceBase;

public interface WebServiceClientFactory<T extends QueryWebServiceBase> {
  T getClient(final String serviceUri);
}
