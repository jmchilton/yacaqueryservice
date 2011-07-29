package com.github.yacaqueryservice;


import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The class is returned for list-style queries issued against a yacaqueryservice web service.
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "ListQueryResults", namespace = "http://github.com/jmchilton/yacaqueryservice/results")
@XmlRootElement(name = "ListQueryResults", namespace = "http://github.com/jmchilton/yacaqueryservice/results")
public class ListQueryResults extends BaseQueryResults {

  @XmlElementWrapper(name = "listResults")
  @XmlElement(name = "ListResults")
  private List<ListResult> results;

  public List<ListResult> getResults() {
    return results;
  }

  public void setResults(final List<ListResult> results) {
    this.results = results;
  }

}
