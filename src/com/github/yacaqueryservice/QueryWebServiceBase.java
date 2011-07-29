package com.github.yacaqueryservice;

import javax.jws.WebMethod;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.github.yacaqueryservice.ql.BaseQuery;
import com.github.yacaqueryservice.ql.ClassIsExpression;
import com.github.yacaqueryservice.ql.ListQuery;
import com.github.yacaqueryservice.ql.ObjectQuery;

@XmlSeeAlso({ListResult.class, BaseQuery.class, ClassIsExpression.class})
public interface QueryWebServiceBase {

  @WebMethod 
  ObjectQueryResults setupQuery(final ObjectQuery query);

  @WebMethod 
  ListQueryResults setupListQuery(final ListQuery query);

  @WebMethod 
  ObjectQueryResults nextBatch(final String queryId);

  @WebMethod 
  ListQueryResults nextListBatch(final String queryId);

}
