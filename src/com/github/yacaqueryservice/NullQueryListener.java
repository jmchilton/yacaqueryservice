package com.github.yacaqueryservice;

import com.github.yacaqueryservice.ql.BaseQuery;

/**
 * A no-op implementation of {@link QueryListener}.
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
public class NullQueryListener implements QueryListener {
  
  public void onQueryReceived(final String queryId, final BaseQuery query) {
  }
}