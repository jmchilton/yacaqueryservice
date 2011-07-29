package com.github.yacaqueryservice.ql;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.google.common.base.Preconditions;

/**
 * Abstraction over identifiers in HQL expressions (for instance in 
 * "select u.id from Unit.id" a target is "Unit.id".
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Target")
public class Target {
  private static AtomicLong objectCount = new AtomicLong(0);
  @XmlAttribute(required = true)
  private String className;
  /**
   * Random unique number to help assign aliases for HQL queries.
   */
  @XmlAttribute(required = true)
  private long objectIndex;
  @XmlElement()
  private List<Association> associations = new ArrayList<Association>();

  public Target() {
    this.objectIndex = objectCount.getAndIncrement();
  }

  public Target(final Class<?> type) {
    setType(type);
  }

  public void setClassName(final String className) {
    this.className = className;
  }

  public String getAlias() {
    return 'a' + className.substring((className.lastIndexOf((int) ((char) '.'))) + 1) + objectIndex;
  }

  public void setType(final Class<?> type) {
    setClassName(type.getName());
  }

  public String getFrom() {
    final StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(className);
    stringBuilder.append(" as ");
    stringBuilder.append(getAlias());
    stringBuilder.append(getAssociationsHql());
    return stringBuilder.toString();
  }

  public String getAssociationsHql() {
    final StringBuilder associationsHql = new StringBuilder();
    for(Association association : associations) {
      final String joinType = association.getType().getType();
      associationsHql.append(String.format(" %s join ", joinType));
      if(association.getFetch()) {
        associationsHql.append("fetch ");
      }
      associationsHql.append(getAlias());
      associationsHql.append(".");
      final String roleName = association.getRoleName();
      // Make sure role name is sanitary for HQL
      Preconditions.checkState(roleName.matches("\\w+"));
      associationsHql.append(roleName);
      associationsHql.append(" as ");
      associationsHql.append(association.getAlias());
      associationsHql.append(association.getAssociationsHql());
    }
    return associationsHql.toString();
  }

  public String getClassName() {
    return className;
  }

  public List<Association> getAssociations() {
    return associations;
  }

  public void addAssociation(final Association association) {
    associations.add(association);
  }

  /**
   * Convenience method to find an association of the specified type.
   * 
   * @param clazz Type to search first
   * @return First association matching specified type.
   */
  public Association getAssociationWithClass(final Class<?> clazz) {
    Association matchingAssociation = null;
    for(Association association : associations) {
      if(association.getClassName().equals(clazz.getName())) {
        matchingAssociation = association;
        break;
      }
    }
    return matchingAssociation;
  }

  public long getObjectIndex() {
    return objectIndex;
  }

  public void setObjectIndex(final long objectIndex) {
    this.objectIndex = objectIndex;
  }

}
