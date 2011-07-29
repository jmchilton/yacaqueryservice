package com.github.yacaqueryservice;

import com.github.yacaqueryservice.ql.BaseQuery;

/**
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
public interface QueryListener {
  
  void onQueryReceived(final String queryId, final BaseQuery query);
  
}