package com.github.yacaqueryservice;


import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The class is returned for object-style queries issued against a yacaqueryservice web service.
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "ObjectQueryResults")
@XmlRootElement(name = "ObjectQueryResults")
public class ObjectQueryResults extends BaseQueryResults {

  @XmlElementWrapper(name = "objectResults")
  @XmlElement(name = "ObjectResults")
  private List<Object> results;

  public List<Object> getResults() {
    return results;
  }

  public void setResults(final List<Object> results) {
    this.results = results;
  }
}
