package com.github.yacaqueryservice;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class encapsulates a single result returned from a yacaqueryservice Web Service 
 * when executing a list-style query. 
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "ListResult")
@XmlRootElement(name = "ListResult")
public class ListResult {
  @XmlElementWrapper(name = "list")
  @XmlElement(name = "List")
  private List<Object> results;

  public ListResult() {
  }

  public ListResult(final List<Object> results) {
    this.results = results;
  }

  public List<Object> getResults() {
    return results;
  }

  public void setResults(final List<Object> results) {
    this.results = results;
  }

}
