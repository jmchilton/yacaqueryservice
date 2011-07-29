package com.github.yacaqueryservice.ql;


import com.github.yacaqueryservice.ql.BinaryComparisonExpression.BinaryOperator;
import com.github.yacaqueryservice.ql.JunctionExpression.LogicalOperator;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

/**
 * This class contains utility methods for creating 
 * various expression objects defined in this package.
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
public class Expressions {

  public static ConstantExpression constant() {
    return new ConstantExpression();
  }

  public static ColumnExpression column(final Target target, final String columnName) {
    return new ColumnExpression(target, columnName);
  }

  public static BetweenComparisonExpression between(final Expression value, final Expression from, final Expression to) {
    final BetweenComparisonExpression expression = new BetweenComparisonExpression();
    expression.setValueCondition(value);
    expression.setFromCondition(from);
    expression.setToCondition(to);
    return expression;
  }

  public static JunctionExpression and(final Expression... conditions) {
    return merge(LogicalOperator.AND, conditions);
  }

  public static JunctionExpression or(final Expression... conditions) {
    return merge(LogicalOperator.OR, conditions);
  }

  private static JunctionExpression merge(final LogicalOperator operator, final Expression... conditions) {
    final JunctionExpression condition = new JunctionExpression();
    condition.setConditions(Lists.newArrayList(conditions));
    condition.setOperator(operator);
    return condition;
  }

  public static BinaryComparisonExpression equal(final Expression left, final Expression right) {
    return new BinaryComparisonExpression(left, right, BinaryOperator.EQUAL);
  }

  public static BinaryComparisonExpression compareWith(final String symbol, final Expression left, final Expression right) {
    final BinaryOperator operator = BinaryOperator.forSymbol(symbol);
    Preconditions.checkArgument(operator != null, "Invalid symbol passed to compareWith -- " + symbol);
    return compareWith(operator, left, right);
  }

  public static BinaryComparisonExpression compareWith(final BinaryOperator operator, final Expression left, final Expression right) {
    return new BinaryComparisonExpression(left, right, operator);
  }

  public static ClassIsExpression classIs(final Target target, final Class<?> clazz) {
    final ClassIsExpression expression = new ClassIsExpression();
    expression.setClass(clazz);
    expression.setTarget(target);
    return expression;
  }

  public static InExpression in(final Expression element, final Iterable<? extends Expression> listExpressions) {
    final InExpression condition = new InExpression();
    condition.setElementCondition(element);
    condition.setListConditions(Lists.newArrayList(listExpressions));
    return condition;
  }

  public static NullityCondition isNull(final Expression condition) {
    return new NullityCondition(condition, true);
  }

  public static NullityCondition isNotNull(final Expression condition) {
    return new NullityCondition(condition, false);
  }

}
