package com.github.yacaqueryservice.ql;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Abstraction over distinct projections in HQL select statements. 
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
@XmlRootElement
public class DistinctProjection extends Projection {
  private Projection projection = null;

  public Projection getProjection() {
    return projection;
  }

  public void setProjection(final Projection projection) {
    this.projection = projection;
  }

  public String getHql() {
    return "distinct " + projection.getHql();
  }
}
