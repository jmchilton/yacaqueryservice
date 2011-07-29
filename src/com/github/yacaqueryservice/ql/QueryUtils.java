package com.github.yacaqueryservice.ql;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.google.common.collect.Lists;

/**
 * Random utility functions related to data structures in this package.
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
public class QueryUtils {
  
  public static synchronized String serializeQuery(final BaseQuery query) {
    return unmarshall(BaseQuery.class, query);
  }

  public static InExpression buildInCondition(final BaseQuery query, final Expression expression, final String valueAsString) {
    return buildInCondition(query, expression, Lists.newArrayList(valueAsString.split(",")));
  }

  public static InExpression buildInCondition(final BaseQuery query, final Expression expression, final List<String> values) {
    final List<Expression> listExpressions = new ArrayList<Expression>(values.size());
    for(String value : values) {
      query.constantForValue(value);
      listExpressions.add(Expressions.constant());
    }
    return Expressions.in(expression, listExpressions);
  }

  public static <T> String unmarshall(final Class<T> clazz, final T object) {
    try {
      JAXBContext context;
      context = JAXBContext.newInstance(clazz);
      Marshaller marshaller = context.createMarshaller();
      final StringWriter writer = new StringWriter();
      marshaller.marshal(object, writer);
      return writer.toString();
    } catch(JAXBException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T marshall(final Class<T> clazz, final String contents) {
    try {
      JAXBContext context;
      context = JAXBContext.newInstance(clazz);
      Unmarshaller marshaller = context.createUnmarshaller();
      final StringReader reader = new StringReader(contents);
      @SuppressWarnings("unchecked")
      final T object = (T) marshaller.unmarshal(reader);
      return object;
    } catch(JAXBException e) {
      throw new RuntimeException(e);
    }
  }
}
