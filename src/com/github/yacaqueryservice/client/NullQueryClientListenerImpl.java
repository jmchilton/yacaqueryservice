package com.github.yacaqueryservice.client;

import com.github.yacaqueryservice.BaseQueryResults;

/**
 * A no-op implementation of {@link ClientQueryListener}.
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
public class NullQueryClientListenerImpl implements ClientQueryListener {

  public void onQueryIssued(final AddressedQuery<?> addressedQuery) {
  }

  public void onQueryReturned(final String queryId, final BaseQueryResults results) {
  }
}