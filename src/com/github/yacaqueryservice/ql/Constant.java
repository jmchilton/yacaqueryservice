package com.github.yacaqueryservice.ql;

import java.text.ParseException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * Abstraction over constants in HQL queries.
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Constant")
public class Constant {
  public static final java.text.DateFormat DATE_FORMATTER = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  // DATE_FORMATTERS are not thread safe so synchronize access
  private static synchronized Object parseDate(final String value) {
    try {
      return DATE_FORMATTER.parse(value);
    } catch(final ParseException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Defines valid data types for {@link Constant}s.
   * 
   * @author John Chilton (jmchilton at gmail dot com)
   *
   */
  @XmlEnum
  public enum TypeEnum {
    LONG(new LongParser()),
    INTEGER(new IntegerParser()),
    DOUBLE(new DoubleParser()),
    DATE(new DateParser()),
    STRING(new StringParser());

    private Parser parser;

    TypeEnum(final Parser parser) {
      this.parser = parser;
    }

  }

  interface Parser {
    Object parse(String value);
  }

  private static class StringParser implements Parser {
    public Object parse(final String value) {
      return value;
    }
  }

  private static class DoubleParser implements Parser {
    public Object parse(final String value) {
      return Double.parseDouble(value);
    }
  }

  private static class LongParser implements Parser {
    public Object parse(final String value) {
      return Long.parseLong(value);
    }
  }

  private static class DateParser implements Parser {
    public Object parse(final String value) {
      return parseDate(value);
    }

  }

  private static class IntegerParser implements Parser {
    public Object parse(final String value) {
      return Integer.parseInt(value);
    }
  }

  private static long constantCount = 0;
  @XmlAttribute(required = true)
  private String name = "const" + constantCount++;
  @XmlAttribute(required = true)
  private String value;
  @XmlAttribute()
  private TypeEnum type = TypeEnum.STRING;

  public TypeEnum getType() {
    return type;
  }

  public void setType(final TypeEnum type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(final String value) {
    this.value = value;
  }

  public Object getParsedValue() {
    return value == null ? null : type.parser.parse(value);
  }

}
