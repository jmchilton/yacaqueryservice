package com.github.yacaqueryservice.ql;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Abstraction over count projections (e.g. count(*)) in HQL.
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
@XmlRootElement
public class CountProjection extends ColumnProjection {
  private boolean distinct = false;

  @Override
  public String getHql() {
    return String.format("count(%s %s)", distinct ? "distinct" : "all", super.getHql());
  }

  public boolean isDistinct() {
    return distinct;
  }

  public void setDistinct(final boolean distinct) {
    this.distinct = distinct;
  }

}
