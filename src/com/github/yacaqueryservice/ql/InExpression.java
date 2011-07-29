package com.github.yacaqueryservice.ql;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Abstraction over relational in expressions.
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class InExpression extends Expression {
  @XmlElement
  private Expression elementCondition = null;
  @XmlElement
  private List<Expression> listConditions = new ArrayList<Expression>();

  public List<Expression> getListConditions() {
    return listConditions;
  }

  public void setListConditions(final List<Expression> listConditions) {
    this.listConditions = listConditions;
  }

  public void addListCondition(final Expression condition) {
    listConditions.add(condition);
  }

  public Expression getElementCondition() {
    return elementCondition;
  }

  public void setElementCondition(final Expression elementCondition) {
    this.elementCondition = elementCondition;
  }

  public String getHql() {
    final StringBuilder builder = new StringBuilder();
    builder.append(elementCondition.getHql());
    builder.append(" IN (");
    boolean first = true;
    for(Expression condition : listConditions) {
      if(first) {
        first = false;
      } else {
        builder.append(", ");
      }
      builder.append(condition.getHql());
    }
    builder.append(")");
    return builder.toString();
  }

}
