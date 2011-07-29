package com.github.yacaqueryservice.ql;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Abstraction defining queries with select lists contain just one
 * projection.
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObjectQuery")
public class ObjectQuery extends BaseQuery {

  public ObjectQuery() {
    super();
  }

  public ObjectQuery(final Target target) {
    super(target);
  }

}
