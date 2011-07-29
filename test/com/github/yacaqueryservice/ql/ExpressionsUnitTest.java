package com.github.yacaqueryservice.ql;

import org.junit.Assert;
import org.junit.Test;

import com.github.yacaqueryservice.example.Organization;
import com.github.yacaqueryservice.example.Unit;

public class ExpressionsUnitTest {

  @Test
  public void testClassIsExpression() {
    final Target target = new Target(Unit.class);
    final ClassIsExpression expression = Expressions.classIs(target, Organization.class);
    Assert.assertEquals(target, expression.getTarget());
    Assert.assertEquals(Organization.class.getName(), expression.getClassName());
    Assert.assertEquals(target.getAlias() + ".class = " + Organization.class.getName(), expression.getHql());
  }

  @Test
  public void testValidWord() {
    final ColumnExpression columnExpression = Expressions.column(new Target(Unit.class), "name");
    Assert.assertEquals("name", columnExpression.getColumnName());
  }

  @Test
  public void testInvalidWord() {
    final ColumnExpression columnExpression = Expressions.column(new Target(Unit.class), "bad ' name");
    Exception e = null;
    try {
      columnExpression.getHql();
    } catch(Exception ie) {
      e = ie;
    }
    Assert.assertNotNull(e);
  }

  @Test
  public void testValidColumnHql() {
    final Target target = new Target(Unit.class);
    final ColumnExpression columnExpression = Expressions.column(target, "name");
    Assert.assertEquals(String.format("%s.%s", target.getAlias(), "name"), columnExpression.getHql());
  }

}
