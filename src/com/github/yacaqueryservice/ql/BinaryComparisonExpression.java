package com.github.yacaqueryservice.ql;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * Abstraction over binary comparison operator expression for relational data 
 * (i.e. <, >=, etc....).
 *  
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class BinaryComparisonExpression extends Expression {

  @XmlEnum
  public enum BinaryOperator {
    LESS_THAN("<"),
    GREATER_THAN(">"),
    LESS_THAN_EQUAL("<="),
    GREAT_THAN_EQUAL(">="),
    EQUAL("=");

    private String symbol;

    private BinaryOperator(final String symbol) {
      this.symbol = symbol;
    }

    public String getSymbol() {
      return symbol;
    }

    public static BinaryOperator forSymbol(final String symbol) {
      BinaryOperator matchingOperator = null;
      for(BinaryOperator operator : values()) {
        if(operator.getSymbol().equals(symbol)) {
          matchingOperator = operator;
          break;
        }
      }
      return matchingOperator;
    }

  }

  @XmlElement
  private Expression leftExpression;
  @XmlElement
  private Expression rightExpression;
  @XmlAttribute
  private BinaryOperator operator;

  public BinaryComparisonExpression(final Expression leftExpression, final Expression rightExpression, final BinaryOperator operator) {
    this.leftExpression = leftExpression;
    this.rightExpression = rightExpression;
    this.operator = operator;
  }

  public BinaryComparisonExpression() {
  }

  public String getHql() {
    final StringBuilder hql = new StringBuilder();
    hql.append(leftExpression.getHql());
    hql.append(" ");
    hql.append(operator.getSymbol());
    hql.append(" ");
    hql.append(rightExpression.getHql());
    return hql.toString();
  }

  public BinaryOperator getOperator() {
    return operator;
  }

  public void setOperator(final BinaryOperator operator) {
    this.operator = operator;
  }

  public Expression getLeftExpression() {
    return leftExpression;
  }

  public void setLeftExpression(final Expression leftExpression) {
    this.leftExpression = leftExpression;
  }

  public Expression getRightExpression() {
    return rightExpression;
  }

  public void setRightExpression(final Expression rightExpression) {
    this.rightExpression = rightExpression;
  }

}
