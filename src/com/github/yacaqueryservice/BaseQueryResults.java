package com.github.yacaqueryservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * Base class of {@link ObjectQueryResults} and {@link ListQueryResults}. Contains 
 * common fields including the queryId (a GUID assigned to this query by the service)
 * and a boolean indicator of whether more results are available (large queries are
 * returned in chunks). 
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "BaseQueryResults")
public class BaseQueryResults {

  @XmlAttribute(name = "queryId")
  private String queryId;

  @XmlAttribute(name = "hasMoreResults")
  private boolean moreResults;

  public boolean isMoreResults() {
    return moreResults;
  }

  public void setMoreResults(final boolean hasMoreResults) {
    this.moreResults = hasMoreResults;
  }

  public String getQueryId() {
    return queryId;
  }

  public void setQueryId(final String queryId) {
    this.queryId = queryId;
  }

}
