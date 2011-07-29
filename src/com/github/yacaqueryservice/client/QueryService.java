package com.github.yacaqueryservice.client;

import java.util.Iterator;

import com.github.yacaqueryservice.ListResult;
import com.github.yacaqueryservice.ql.ListQuery;
import com.github.yacaqueryservice.ql.ObjectQuery;

public interface QueryService {

  <T> Iterator<T> query(AddressedQuery<ObjectQuery> query);

  Iterator<ListResult> queryForLists(AddressedQuery<ListQuery> query);

}
