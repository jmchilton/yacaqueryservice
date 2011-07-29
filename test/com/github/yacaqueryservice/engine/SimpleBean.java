package com.github.yacaqueryservice.engine;

public class SimpleBean {

  public SimpleBean(final String foo) {
    setFoo(foo);
  }

  public SimpleBean() {

  }

  private String foo;

  public void setFoo(final String foo) {
    this.foo = foo;
  }

  public String getFoo() {
    return foo;
  }

}