package com.github.yacaqueryservice.client;

import com.github.yacaqueryservice.BaseQueryResults;

/**
 * Instances of this class may be associated with a {@link QueryService} 
 * object to record or otherwise handle query related events.
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
public interface ClientQueryListener {

  void onQueryIssued(AddressedQuery<?> query);

  void onQueryReturned(String queryId, BaseQueryResults results);

}
