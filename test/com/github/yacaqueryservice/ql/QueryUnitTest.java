package com.github.yacaqueryservice.ql;

import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.StringUtils;

import com.github.yacaqueryservice.example.Organization;
import com.github.yacaqueryservice.example.Unit;

public class QueryUnitTest {

  @Test
  public void testAlias() {
    Target target = new Target();
    target.setClassName("moo");
    Target target2 = new Target();
    target2.setClassName("moo");

    Assert.assertTrue(StringUtils.hasText(target.getAlias()));
    Assert.assertTrue(target.getAlias() != target2.getAlias());
  }

  @Test
  public void testConstantForValue() {
    final ObjectQuery query = new ObjectQuery();
    query.constantForValue("moo");
    final Constant constant = query.getConstants().get(0);
    Assert.assertEquals("moo", constant.getParsedValue());

    query.integerConstantForValue("1");
    Assert.assertEquals(1L, query.getConstants().get(1).getParsedValue());

    query.dateConstantForValue("1984-07-09 12:13:09");
    final Calendar cal = Calendar.getInstance(TimeZone.getDefault());
    cal.set(Calendar.YEAR, 1984);
    cal.set(Calendar.MONTH, 6);
    cal.set(Calendar.DATE, 9);
    cal.set(Calendar.HOUR_OF_DAY, 12);
    cal.set(Calendar.MINUTE, 13);
    cal.set(Calendar.SECOND, 9);
    cal.set(Calendar.MILLISECOND, 0);
    Assert.assertEquals(cal.getTime(), query.getConstants().get(2).getParsedValue());

    query.doubleConstantForValue("5.6");
    Assert.assertEquals(5.6, query.getConstants().get(3).getParsedValue());

  }

  @Test
  public void test1WhereStatement() {
    Target target = new Target(Unit.class);
    ObjectQuery queryBuilder = new ObjectQuery(target);
    queryBuilder.constantForValue("3");
    queryBuilder.getCondition().addCondition(Expressions.equal(Expressions.column(target, "id"), Expressions.constant()));
    String result = queryBuilder.getHql();
    final String expected = new StringBuilder(target.getAlias()).append(" where (").append(target.getAlias()).append(".id = ?)").toString();
    Assert
        .assertTrue(result.contains(expected));
  }

  @Test
  public void test2WhereStatements() {
    Target target = new Target(Unit.class);
    ObjectQuery queryBuilder = new ObjectQuery(target);
    queryBuilder.constantForValue("3");
    queryBuilder.getCondition().addCondition(Expressions.equal(Expressions.column(target, "id"), Expressions.constant()));
    queryBuilder.constantForValue("bar");
    queryBuilder.getCondition().addCondition(Expressions.equal(Expressions.column(target, "foo"), Expressions.constant()));
    String result = queryBuilder.getHql();
    String condition = String.format("%s where (%s.id = ?) AND (%s.foo = ?)", target.getAlias(), target.getAlias(), target.getAlias());
    Assert.assertTrue(result.contains(condition));
  }

  @Test
  public void testCountOnly() {
    Target target = new Target(Unit.class);
    final ObjectQuery query = new ObjectQuery(target);
    query.setCountOnly(true);
    final String result = query.getHql();
    String select = String.format("select count(%s) from %s as %s", target.getAlias(), target.getClassName(), target.getAlias());
    Assert.assertTrue(result.contains(select));
  }

  @Test
  public void testSimplestHql() {
    Target target = new Target(Unit.class);
    String result = new ObjectQuery(target).getHql();
    String hql = String.format("select %s from com.github.yacaqueryservice.example.Unit as %s", target.getAlias(), target.getAlias());
    Assert.assertTrue(result.contains(hql));
  }

  @Test
  public void test1Join() {
    Target target = new Target(Unit.class);
    final Association association = new Association();
    association.setType(Organization.class);
    association.setRoleName("organization");
    target.getAssociations().add(association);
    final String result = new ObjectQuery(target).getHql();
    String hql = String.format("%s left join %s.organization as %s", target.getAlias(), target.getAlias(), association.getAlias());
    Assert.assertTrue(result.contains(hql));
  }

  @Test
  public void testFetchJoin() {
    final Target target = new Target(Unit.class);
    final Association association = new Association();
    association.setType(Organization.class);
    association.setRoleName("organization");
    association.setFetch(true);
    target.getAssociations().add(association);
    final String result = new ObjectQuery(target).getHql();
    Assert.assertTrue(result.contains("left join fetch"));
  }

  @Test
  public void test2Join() {
    final Target target = new Target();
    target.setType(Unit.class);
    final Association association1 = new Association();
    association1.setType(Organization.class);
    association1.setRoleName("organization");
    final Association association2 = new Association();
    association2.setType(Unit.class);
    association2.setRoleName("units");
    target.getAssociations().add(association1);
    association1.getAssociations().add(association2);
    final String result = new ObjectQuery(target).getHql();
    String cond1 = String.format("%s left join %s.organization as %s", target.getAlias(), target.getAlias(), association1.getAlias());
    Assert.assertTrue(result.contains(cond1));
    String cond2 = String.format(" left join %s.units as %s", association1.getAlias(), association2.getAlias());
    Assert.assertTrue(result.contains(cond2));
  }
}
