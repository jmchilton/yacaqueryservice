package com.github.yacaqueryservice.engine;


import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import com.github.yacaqueryservice.example.Organization;
import com.github.yacaqueryservice.example.Unit;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class BeanSanitizerImplUnitTest {

  public static class FakeInstrumentedUnitCGLIB extends Unit {
  }

  public static class FakeInstrumentedUnitjavassist extends Unit {
  }
  
  @Test
  public void testSanitizeSimpleType() {
    final Integer seven = 7;
    Assert.assertEquals(Integer.valueOf(7), sanitize(seven));
  }
  
  @Test
  public void testValidCopy() {
    final SimpleBean oldA = new SimpleBean("str1");
    final SimpleBean newA = new BeanSanitizerImpl().sanitize(oldA, Lists.<String>newArrayList());
    Assert.assertEquals("str1", newA.getFoo());
    Assert.assertTrue(oldA != newA);
  }

  @Test
  public void testIgnoreNoMatchingSetter() {
    final SimpleBeanWithExtras oldA = new SimpleBeanWithExtras("str1");
    final SimpleBeanWithExtras newA = sanitize(oldA);
    Assert.assertEquals("str1", newA.getFoo());
    Assert.assertTrue(oldA != newA);
  }

  private <T> T sanitize(final T object) {
    return sanitize(object, null);
  }
  
  private <T> T sanitize(final T object, final List<String> eagerAssociations) {
    return new BeanSanitizerImpl().sanitize(object, eagerAssociations);
  }

  @Test
  public void testDoNotCopyLazyAssociations() {
    final Organization org = organizationWithAssociatedUnits();
    final Organization orgCopy = sanitize(org);
    Assert.assertNull(orgCopy.getUnits());
  }
  
  @Test
  public void testCopySimpleAssociation() {
    final Unit unit = unitWithAssociatedOrganization();
    final Unit unitCopy = sanitize(unit, Lists.newArrayList(Unit.class.getName() + ">organization"));
    assertDifferentWithSameId(unit, unitCopy);
    assertDifferentWithSameId(unit.getOrganization(), unitCopy.getOrganization());
  }

  @Test
  public void testCopyEmptyCollectionAssociation() {
    final Organization org = newOrganization();
    org.setUnits(Lists.<Unit>newArrayList());
    final Organization orgCopy = sanitize(org, Lists.newArrayList(Organization.class.getName() + ">units"));
    assertDifferentWithSameId(org, orgCopy);
  }
  
  private void assertDifferentWithSameId(final Organization org, final Organization orgCopy) {
    Assert.assertFalse(org == orgCopy);
    Assert.assertEquals(org.getId(), orgCopy.getId());    
  }

  private void assertDifferentWithSameId(final Unit unit, final Unit unitCopy) {
    Assert.assertFalse(unit == unitCopy);
    Assert.assertEquals(unit.getId(), unitCopy.getId());    
  }

  @Test
  public void testCopyCollectionAssociation() {
    final Organization org = organizationWithAssociatedUnits();
    final Organization orgCopy = sanitize(org, Lists.newArrayList(Organization.class.getName() + ">units"));
    assertDifferentWithSameId(org, orgCopy);
    assertDifferentWithSameId(org.getUnits().iterator().next(), orgCopy.getUnits().iterator().next());
  }
  
  @Test
  public void testCopyAsParentIfInstrumented() {
    Assert.assertFalse(sanitize(new FakeInstrumentedUnitCGLIB()) instanceof FakeInstrumentedUnitCGLIB);
    Assert.assertFalse(sanitize(new FakeInstrumentedUnitjavassist()) instanceof FakeInstrumentedUnitjavassist);
  }
  
  private Unit unitWithAssociatedOrganization() {
    final Organization org = newOrganization();
    final Unit unit1 = newUnit();
    unit1.setOrganization(org);
    return unit1;
  }

  private Organization organizationWithAssociatedUnits() {
    final Organization org = newOrganization();
    final Unit unit1 = newUnit();
    org.setUnits(Sets.newHashSet(unit1));
    return org;
  }

  private Unit newUnit() {
    final Unit unit1 = new Unit();
    unit1.setId(UUID.randomUUID().toString());
    return unit1;
  }

  private Organization newOrganization() {
    final Organization org = new Organization();
    org.setId(UUID.randomUUID().toString());
    return org;
  }

}
