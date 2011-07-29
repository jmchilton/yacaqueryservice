package com.github.yacaqueryservice.ql;


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.github.yacaqueryservice.ql.Constant.TypeEnum;
import com.google.common.base.Preconditions;

/**
 * Base class for queries that can be generated for yacaquery services.
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseQuery")
public abstract class BaseQuery {
  @XmlElement()
  private JunctionExpression condition = new JunctionExpression();
  @XmlElement()
  private List<Constant> constants = new ArrayList<Constant>();
  @XmlElement()
  private Target target;
  @XmlAttribute()
  private boolean countOnly = false;

  public BaseQuery() {
  }

  public BaseQuery(final Target target) {
    this.target = target;
  }

  protected String getSelectListHql() {
    final StringBuilder selectListHql = new StringBuilder();
    final String aliasName = target.getAlias();
    String select = aliasName;
    if(countOnly) {
      select = "count(" + aliasName + ")";
    }
    selectListHql.append(select);

    return selectListHql.toString();
  }

  public String getHql() {
    Preconditions.checkNotNull(target, "getHql() called with on Query object with no target.");
    final String from = target.getFrom();
    String whereStatementsHql = "";
    if(!condition.getConditions().isEmpty()) {
      whereStatementsHql = "where " + condition.getHql();
    }
    final String select = getSelectListHql();
    return new StringBuilder().append("select ").append(select).append(" from ").append(from).append(" ").append(whereStatementsHql).toString();
  }

  public Constant constantForValue(final String value) {
    return constantForValue(value, TypeEnum.STRING);
  }

  public Constant integerConstantForValue(final String value) {
    return constantForValue(value, TypeEnum.LONG);
  }

  public Constant doubleConstantForValue(final String value) {
    return constantForValue(value, TypeEnum.DOUBLE);
  }

  public Constant dateConstantForValue(final String value) {
    return constantForValue(value, TypeEnum.DATE);
  }

  public Constant constantForValue(final String value, final TypeEnum type) {
    Constant constant = new Constant();
    constant.setValue(value);
    constant.setType(type);
    constants.add(constant);
    return constant;
  }

  public List<Object> getConstantValues() {
    final List<Object> constantValues = new ArrayList<Object>(constants.size());
    for(Constant constant : constants) {
      constantValues.add(constant.getParsedValue());
    }
    return constantValues;
  }

  public BaseQuery whereIdForTargetIn(final Target target, final List<String> ids) {
    final List<Expression> listExpressions = new ArrayList<Expression>(ids.size());
    for(final String id : ids) {
      if(id != null) {
        constantForValue(id);
        listExpressions.add(Expressions.constant());
      }
    }
    condition.addCondition(Expressions.in(Expressions.column(target, "id"), listExpressions));
    return this;
  }

  public Target getTarget() {
    return target;
  }

  public void setTarget(final Target target) {
    this.target = target;
  }

  public JunctionExpression getCondition() {
    return condition;
  }

  public void setCondition(final JunctionExpression condition) {
    this.condition = condition;
  }

  public List<Constant> getConstants() {
    return constants;
  }

  public void setConstants(final List<Constant> constants) {
    this.constants = constants;
  }

  public boolean getCountOnly() {
    return countOnly;
  }

  public void setCountOnly(final boolean countOnly) {
    this.countOnly = countOnly;
  }

  public Iterable<String> getEagerAssociations() {
    final List<String> eagerAssociations = new ArrayList<String>();
    List<Target> targetsToSearch = new ArrayList<Target>();
    List<String> previousTargetClassName = new ArrayList<String>();
    targetsToSearch.add(target);
    previousTargetClassName.add(null);
    while(!targetsToSearch.isEmpty()) {
      final Target target = targetsToSearch.remove(0);
      final String previousClassName = previousTargetClassName.remove(0);

      for(final Association association : target.getAssociations()) {
        targetsToSearch.add(association);
        previousTargetClassName.add(target.getClassName());
      }

      if(target instanceof Association) {
        final Association association = (Association) target;
        if(association.getFetch()) {
          eagerAssociations.add(previousClassName + ">" + association.getRoleName());
        }
      }
    }
    return eagerAssociations;
  }

}
