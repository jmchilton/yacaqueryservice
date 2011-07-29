package com.github.yacaqueryservice.engine;

public class SimpleBeanWithExtras extends SimpleBean {

  public SimpleBeanWithExtras() {
    super();
  }

  public SimpleBeanWithExtras(final String foo) {
    super(foo);
  }

  public String getDog(final String breed) {
    return "Breed of dog is " + breed;
  }

  public String getException() {
    throw new RuntimeException();
  }

  public void setException(final String exceptionString) {
    throw new RuntimeException();
  }

}
