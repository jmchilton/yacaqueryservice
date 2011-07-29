package com.github.yacaqueryservice.ql;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Abstraction over HQL type checking operator expressions.
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "classIs")
public class ClassIsExpression extends Expression {
  private Target target = null;
  private String className = null;

  public ClassIsExpression() {
  }

  public String getHql() {
    return String.format("%s.class = %s", target.getAlias(), className);
  }

  public Target getTarget() {
    return target;
  }

  public void setTarget(final Target target) {
    this.target = target;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(final String className) {
    this.className = className;
  }

  public void setClass(final Class<?> clazz) {
    this.className = clazz.getName();
  }

}
