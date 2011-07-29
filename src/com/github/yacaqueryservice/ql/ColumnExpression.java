package com.github.yacaqueryservice.ql;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Abstraction of relation column expressions (such as alias.columnname).
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class ColumnExpression extends Expression {
  private Target target;
  private String columnName;

  public ColumnExpression(final Target target, final String columnName) {
    this.target = target;
    this.columnName = columnName;
  }

  public ColumnExpression() {
  }

  public String getHql() {
    if(!columnName.matches("\\w+")) {
      throw new IllegalStateException("Invalid column name specified.");
    }
    return target.getAlias() + "." + columnName;
  }

  public Target getTarget() {
    return target;
  }

  public void setTarget(final Target target) {
    this.target = target;
  }

  public String getColumnName() {
    return columnName;
  }

  public void setColumnName(final String columnName) {
    this.columnName = columnName;
  }

}
