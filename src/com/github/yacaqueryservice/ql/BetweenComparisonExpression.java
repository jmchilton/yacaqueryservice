package com.github.yacaqueryservice.ql;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Abstraction over the relational BETWEEN operator.
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class BetweenComparisonExpression extends Expression {
  @XmlElement
  private Expression valueCondition = null;
  @XmlElement
  private Expression fromCondition = null;
  @XmlElement
  private Expression toCondition = null;

  public String getHql() {
    return valueCondition.getHql() + " BETWEEN " + fromCondition.getHql() + " AND " + toCondition.getHql();
  }

  public Expression getValueCondition() {
    return valueCondition;
  }

  public void setValueCondition(final Expression valueCondition) {
    this.valueCondition = valueCondition;
  }

  public Expression getFromCondition() {
    return fromCondition;
  }

  public void setFromCondition(final Expression fromCondition) {
    this.fromCondition = fromCondition;
  }

  public Expression getToCondition() {
    return toCondition;
  }

  public void setToCondition(final Expression toCondition) {
    this.toCondition = toCondition;
  }

}
