package com.github.yacaqueryservice.ql;

import org.junit.Assert;
import org.junit.Test;

import com.github.yacaqueryservice.example.Unit;
import com.github.yacaqueryservice.ql.Association.AssociationType;

public class AssociationUnitTest {

  @Test
  public void testNoFetchByDefault() {
    final Association association = new Association(Unit.class, "unit");
    Assert.assertFalse(association.getFetch());
  }
  
  @Test
  public void testDefaultLeftJoin() {
    final Association association = new Association(Unit.class, "unit");
    Assert.assertEquals(AssociationType.LEFT, association.getType());
  }
  
  @Test
  public void testSetRightJoinConstructor() {
    final Association association = new Association(Unit.class, "unit", AssociationType.RIGHT);
    Assert.assertEquals(AssociationType.RIGHT, association.getType());
  }
  
  @Test
  public void testSetRightJoin() {
    final Association association = new Association(Unit.class, "unit", false);
    association.setType(AssociationType.RIGHT);
    Assert.assertEquals(AssociationType.RIGHT, association.getType());
  }

  @Test
  public void testSetFetchConstructor() {
    final Association association = new Association(Unit.class, "unit", true);
    Assert.assertTrue(association.getFetch());
  }

  @Test
  public void testSetFetch() {
    final Association association = new Association(Unit.class, "unit");
    association.setFetch(true);
    Assert.assertTrue(association.getFetch());
  }
  

}
