package com.github.yacaqueryservice.ql;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Abstraction over relational null checking expressions.
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class NullityCondition extends Expression {
  @XmlElement
  private Expression expression;
  @XmlAttribute
  private boolean isNull = true;

  public NullityCondition(final Expression expression, final boolean isNull) {
    this.expression = expression;
    this.isNull = isNull;
  }

  public NullityCondition() {
    super();
  }

  public Expression getExpression() {
    return expression;
  }

  public void setExpression(final Expression expression) {
    this.expression = expression;
  }

  public boolean getIsNull() {
    return isNull;
  }

  public void setIsNull(final boolean isNull) {
    this.isNull = isNull;
  }

  public String getHql() {
    return expression.getHql() + " IS " + (isNull ? "" : "NOT ") + "NULL";
  }
}
