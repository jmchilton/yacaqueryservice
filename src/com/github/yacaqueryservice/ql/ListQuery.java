package com.github.yacaqueryservice.ql;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

/**
 * Abstraction defining queries with select lists with more than one
 * projection.
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayQuery")
public class ListQuery extends BaseQuery {
  @XmlElement
  private List<Projection> selectList = null;

  // No-arg constructor for CXF
  public ListQuery() {

  }

  public ListQuery(final Target target) {
    super(target);
  }

  public ListQuery(final Target target, final List<Projection> selectList) {
    super(target);
    Preconditions.checkArgument(selectList.size() > 0);
    this.selectList = Lists.newArrayList(selectList);
  }

  public List<Projection> getSelectList() {
    return selectList;
  }

  public void setColumns(final List<ColumnExpression> columns) {
    setSelectList(Projections.forColumns(columns));
  }

  public void setSelectList(final List<Projection> selectList) {
    this.selectList = selectList;
  }

  @Override
  protected String getSelectListHql() {
    final StringBuilder selectListHql = new StringBuilder();
    boolean first = true;
    for(final Projection expression : selectList) {
      if(first) {
        first = false;
      } else {
        selectListHql.append(", ");
      }
      selectListHql.append(expression.getHql());
    }
    return selectListHql.toString();
  }

}
