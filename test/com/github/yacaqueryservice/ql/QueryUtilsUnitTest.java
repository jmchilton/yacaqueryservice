package com.github.yacaqueryservice.ql;

import org.junit.Assert;
import org.junit.Test;

import com.github.yacaqueryservice.example.Unit;

public class QueryUtilsUnitTest {

  @Test
  public void testNamespace() {
    final String xml = QueryUtils.serializeQuery(new ObjectQuery());
    Assert.assertTrue(xml.contains("http://github.com/jmchilton/yacaqueryservice/query"));
  }
  
  @Test
  public void testSerialization() throws Exception {
    final ObjectQuery query = new ObjectQuery();
    query.setTarget(new Target(Unit.class));
    final String xml = QueryUtils.unmarshall(ObjectQuery.class, query);
    final ObjectQuery marshalledQuery = QueryUtils.marshall(ObjectQuery.class, xml);
    Assert.assertTrue(marshalledQuery.getTarget().getClassName().equals(Unit.class.getName()));
  }
  
  @Test
  public void testBuildInCondition() {
    final Target target = new Target(Unit.class);
    final ObjectQuery query = new ObjectQuery(target);
    final InExpression inExpression = QueryUtils.buildInCondition(query, Expressions.column(target, "id"), "123,456,789");
    Assert.assertTrue(query.getConstants().get(0).getValue().equals("123"));
    Assert.assertTrue(query.getConstants().get(1).getValue().equals("456"));
    Assert.assertTrue(query.getConstants().get(2).getValue().equals("789"));
    Assert.assertEquals(inExpression.getListConditions().size(), 3);
    for(final Expression listCondition : inExpression.getListConditions()) {
      Assert.assertTrue(listCondition instanceof ConstantExpression);
    }
  }

}

