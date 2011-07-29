package com.github.yacaqueryservice.engine;


import java.util.Iterator;
import java.util.List;

/**
 * This interface provides the interface between the service layer and the database much like caCore SDK's ApplicationService interface.
 * Implementations of this interface also "clean" hibernate beans to prevent consumers of this interface from encountering lazy initialization
 * exceptions.
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 * 
 */
public interface QueryEngine {

  /**
   * Query for a sequence of objects, for instance "select bean from BeanType bean".
   * 
   * @param <T> Bean class
   * @param query
   * @return
   */
  <T> Iterator<T> query(QueryDescription query);

  /**
   * For queries with multiple simultaneous select expression, for instance "select bean.prop1, bean.prop2 from BeanType bean".
   * 
   * @param <T> Bean class
   * @param query
   * @return
   */
  Iterator<List<Object>> queryForLists(QueryDescription query);

}
