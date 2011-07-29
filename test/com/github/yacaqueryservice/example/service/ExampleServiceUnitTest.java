package com.github.yacaqueryservice.example.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.github.yacaqueryservice.client.AddressedQuery;
import com.github.yacaqueryservice.client.QueryServiceImpl;
import com.github.yacaqueryservice.client.WebServiceClientFactoryImpl;
import com.github.yacaqueryservice.example.Unit;
import com.github.yacaqueryservice.ql.ObjectQuery;
import com.github.yacaqueryservice.ql.Target;

@ContextConfiguration("service-definition-context.xml")
public class ExampleServiceUnitTest extends AbstractJUnit4SpringContextTests {

  @Test
  public void test() {
    final WebServiceClientFactoryImpl<ExampleService> factory = new WebServiceClientFactoryImpl<ExampleService>(ExampleService.class);
    //final ExampleService exampleService = factory.getClient("http://localhost:9000/query");
    final QueryServiceImpl<ExampleService> queryService = new QueryServiceImpl<ExampleService>();
    queryService.setWebServiceClientFactory(factory);
    final AddressedQuery<ObjectQuery> addressedQuery = new AddressedQuery<ObjectQuery>();
    addressedQuery.setServiceUri("http://localhost:9000/query");
    ObjectQuery oq = new ObjectQuery(new Target(Unit.class));
    addressedQuery.setQuery(oq);
    Assert.assertFalse(queryService.query(addressedQuery).hasNext());

  }
  
}
