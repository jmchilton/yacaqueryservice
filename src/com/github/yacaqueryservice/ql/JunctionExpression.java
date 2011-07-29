package com.github.yacaqueryservice.ql;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * Abstraction of relational AND and OR expressions.
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class JunctionExpression extends Expression {
  /**
   * Defines junction type for {@link JunctionExpression}s (AND / OR).
   * 
   * @author John Chilton (jmchilton at gmail dot com)
   *
   */
  @XmlEnum
  public static enum LogicalOperator {
    AND, OR
  }

  @XmlAttribute
  private LogicalOperator operator = LogicalOperator.AND;
  @XmlElement
  private List<Expression> conditions = new ArrayList<Expression>();

  public LogicalOperator getOperator() {
    return operator;
  }

  public void setOperator(final LogicalOperator operator) {
    this.operator = operator;
  }

  public List<Expression> getConditions() {
    return conditions;
  }

  public void setConditions(final List<Expression> conditions) {
    this.conditions = conditions;
  }

  public void addCondition(final Expression condition) {
    this.conditions.add(condition);
  }

  public String getHql() {
    final StringBuilder hql = new StringBuilder();
    boolean first = true;
    for(final Expression condition : conditions) {
      if(first) {
        first = false;
      } else {
        hql.append(" " + operator.name() + " ");
      }
      hql.append("(");
      hql.append(condition.getHql());
      hql.append(")");
    }
    return hql.toString();
  }
}
