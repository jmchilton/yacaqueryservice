package com.github.yacaqueryservice.ql;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * Abstraction of a database join.
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Association")
public class Association extends Target {
  /**
   * Defines the various types of database joins implemented.
   * 
   * @author John Chilton (jmchilton at gmail dot com)
   *
   */
  @XmlEnum
  public enum AssociationType {
    LEFT("left"),
    RIGHT("right"),
    INNER("inner"),
    FULL("full");

    private String type;

    private AssociationType(final String type) {
      this.type = type;
    }

    public String getType() {
      return type;
    }

  }

  @XmlAttribute(required = false)
  private AssociationType type = AssociationType.LEFT;
  @XmlAttribute(required = true)
  private String roleName;
  @XmlAttribute(required = true)
  private boolean fetch;

  public Association() {
  }

  public Association(final Class<?> type, final String roleName) {
    this(type, roleName, false);
  }

  public Association(final Class<?> type, final String roleName, final AssociationType associationType) {
    this(type, roleName, false, associationType);
  }

  public Association(final Class<?> type, final String roleName, final boolean fetch) {
    this(type, roleName, fetch, AssociationType.LEFT);
  }

  public Association(final Class<?> type, final String roleName, final boolean fetch, final AssociationType associationType) {
    super(type);
    this.roleName = roleName;
    this.fetch = fetch;
    this.type = associationType;
  }

  public AssociationType getType() {
    return type;
  }

  public void setType(final AssociationType type) {
    this.type = type;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(final String roleName) {
    this.roleName = roleName;
  }

  public boolean getFetch() {
    return fetch;
  }

  public void setFetch(final boolean fetch) {
    this.fetch = fetch;
  }

}
