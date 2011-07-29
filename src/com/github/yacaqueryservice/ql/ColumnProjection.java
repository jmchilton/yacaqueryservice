package com.github.yacaqueryservice.ql;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Abstraction of column projections (i.e. specifying columns or fields 
 * in an HQL select clause.) 
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
@XmlRootElement
public class ColumnProjection extends Projection {
  private ColumnExpression expression = null;

  public ColumnExpression getExpression() {
    return expression;
  }

  public void setColumnExpression(final ColumnExpression expression) {
    this.expression = expression;
  }

  public String getHql() {
    return expression.getHql();
  }

}
