package com.github.yacaqueryservice.ql;


import org.junit.Assert;
import org.junit.Test;

import com.github.yacaqueryservice.example.Unit;

public class ProjectionsUnitTest {

  private void assertHqlIs(final HasHql hasHql, final String hql) {
    Assert.assertEquals(hql, hasHql.getHql());
  }

  @Test
  public void testRowCount() {
    assertHqlIs(Projections.rowCount(), "count(*)");
  }

  @Test
  public void testSimpleProjection() {
    final Target target = new Target(Unit.class);
    final ColumnExpression expression = Expressions.column(target, "name");
    assertHqlIs(Projections.forColumn(expression), String.format("%s.name", target.getAlias()));
  }

  @Test
  public void testCountProjection() {
    final Target target = new Target(Unit.class);
    final ColumnExpression expression = Expressions.column(target, "name");
    assertHqlIs(Projections.count(expression, true), String.format("count(distinct %s.name)", target.getAlias()));
    assertHqlIs(Projections.count(expression, false), String.format("count(all %s.name)", target.getAlias()));
  }

  @Test
  public void testDistinctProjection() {
    final Target target = new Target(Unit.class);
    final ColumnExpression expression = Expressions.column(target, "name");
    assertHqlIs(Projections.distinct(Projections.forColumn(expression)), String.format("distinct %s.name", target.getAlias()));
  }

}
