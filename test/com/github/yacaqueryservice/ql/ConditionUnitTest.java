package com.github.yacaqueryservice.ql;

import static com.github.yacaqueryservice.ql.Expressions.between;
import static com.github.yacaqueryservice.ql.Expressions.compareWith;
import static com.github.yacaqueryservice.ql.Expressions.constant;
import static com.github.yacaqueryservice.ql.Expressions.equal;
import static com.github.yacaqueryservice.ql.Expressions.in;
import static com.github.yacaqueryservice.ql.Expressions.isNotNull;
import static com.github.yacaqueryservice.ql.Expressions.isNull;

import org.junit.Assert;
import org.junit.Test;

import com.github.yacaqueryservice.ql.BinaryComparisonExpression.BinaryOperator;
import com.google.common.collect.Lists;

public class ConditionUnitTest {

  @Test
  public void testConstantCondition() {
    Assert.assertEquals("?", constant().getHql());
  }

  @Test
  public void testBetweenCondition() {
    Assert.assertEquals("? BETWEEN ? AND ?", between(constant(), constant(), constant()).getHql());
  }

  @Test
  public void testEquals() {
    Assert.assertEquals("? = ?", equal(constant(), constant()).getHql());
  }

  @Test
  public void testCompare() {
    Assert.assertEquals("? <= ?", compareWith(BinaryOperator.LESS_THAN_EQUAL, constant(), constant()).getHql());
  }

  @Test
  public void testIn() {
    Assert.assertEquals("? IN (?, ?)", in(constant(), Lists.newArrayList(constant(), constant())).getHql());
  }

  @Test
  public void testIsNotNull() {
    Assert.assertEquals("? IS NOT NULL", isNotNull(constant()).getHql());
  }

  @Test
  public void testIsNull() {
    Assert.assertEquals("? IS NULL", isNull(constant()).getHql());
  }

}
