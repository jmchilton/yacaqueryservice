package com.github.yacaqueryservice.engine;

import java.lang.reflect.Method;

class ReflectionUtils {

  static <T> T newInstance(final String className) {
    try {
      @SuppressWarnings("unchecked")
      final Class<T> clazz = (Class<T>) Class.forName(className);
      return clazz.newInstance();
    } catch(Exception e) {
      throw new RuntimeException(e);
    }
  }

  static Method getMethod(final Class<?> clazz, final String methodName, final Class<?>... parameterTypes) {
    try {
      return clazz.getMethod(methodName, parameterTypes);
    } catch(NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

}
