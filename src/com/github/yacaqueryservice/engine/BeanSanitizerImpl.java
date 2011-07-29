package com.github.yacaqueryservice.engine;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

class BeanSanitizerImpl implements BeanSanitizer {
  private static Set<Class<?>> simpleTypes = Sets.<Class<?>>newHashSet(
      Integer.class,
      int.class,
      Double.class,
      double.class,
      Long.class,
      long.class,
      Short.class,
      short.class,
      Boolean.class,
      boolean.class,
      Float.class,
      float.class,
      String.class,
      java.sql.Date.class,
      java.util.Date.class);

  public <T> T sanitize(final T bean, final Iterable<String> eagerAssociations) {
    @SuppressWarnings("unchecked")
    final T sanitizedBean = (T) new SanitizerContext(eagerAssociations).copy(bean);
    return sanitizedBean;
  }

  @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = {"REC_CATCH_EXCEPTION", "DE_MIGHT_IGNORE"}, justification = "Mean to suppress and ignore exceptions.")
  private static class SanitizerContext {
    private Iterable<String> eagerAssociations;

    SanitizerContext(final Iterable<String> eagerAssociations) {
      this.eagerAssociations = eagerAssociations == null ? Lists.<String>newArrayList() : eagerAssociations;
    }

    Object copy(final Object bean) {
      if(simpleTypes.contains(bean.getClass())) {
        return bean;
      } else {
        final Object copy = copyObject(bean);
        return copy;
      }
    }

    private Object copyObject(final Object bean) {
      final String className = uninstrumentedClass(bean).getName();
      final Object copy = ReflectionUtils.newInstance(className);
      final Class<?> beanClass = copy.getClass();
      for(final Method method : beanClass.getMethods()) {
        final String methodName = method.getName();
        if(!methodName.startsWith("get")) {
          continue;
        }
        if(method.getParameterTypes().length != 0) {
          continue;
        }
        if(!simpleTypes.contains(method.getReturnType())) {
          continue;
        }
        final String setMethodName = "s" + methodName.substring(1);
        Method setMethod;
        try {
          setMethod = beanClass.getMethod(setMethodName, method.getReturnType());
        } catch(final NoSuchMethodException e) {
          // If no such setter exists, this wasn't a bean property, just proceed to next method.
          continue;
        }
        Object obtainedObject;
        try {
          obtainedObject = method.invoke(bean);
        } catch(final Exception e) {
          continue;
        }
        if(obtainedObject == null) {
          continue;
        }
        org.springframework.util.ReflectionUtils.invokeMethod(setMethod, copy, obtainedObject);
      }

      for(final String eagerAssociation : eagerAssociations) {
        if(!eagerAssociation.startsWith(className + ">")) {
          continue;
        }
        final String associationName = eagerAssociation.substring(className.length() + 1);
        copyAssociation(bean, beanClass, copy, associationName);
      }

      return copy;
    }

    private void copyAssociation(final Object bean, final Class<?> beanClass, final Object copy, final String associationName) {
      final String getMethodName = getMethodName(associationName);
      final Method getMethod = ReflectionUtils.getMethod(beanClass, getMethodName);
      final Object associatedObject;
      try {
        associatedObject = getMethod.invoke(bean);
      } catch(Exception e) {
        return;
      }
      if(associatedObject != null) {
        final Class<?> associationType = ReflectionUtils.getMethod(beanClass, getMethodName).getReturnType();
        final Object associationCopy;
        try {
          Preconditions.checkNotNull(associationType);
          if(associationType.isAssignableFrom(Collection.class)) {
            associationCopy = copyCollection((Collection<?>) associatedObject);
          } else {
            associationCopy = copy(associatedObject);
          }
        } catch(Exception e) {
          return;
        }
        final String setMethodName = "s" + getMethodName.substring(1);
        final Method setMethod = ReflectionUtils.getMethod(beanClass, setMethodName, associationType);
        org.springframework.util.ReflectionUtils.invokeMethod(setMethod, copy, associationCopy);
      }
    }

    
    @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = {"DM_CONVERT_CASE"}, justification = "Doesn't need to be internationalized, not user facing.")
    private String getMethodName(final String associationName) {
      return "get" + associationName.substring(0, 1).toUpperCase() + associationName.substring(1);
    }

    private Class<?> uninstrumentedClass(final Class<?> inputClass) {
      Class<?> clazz = inputClass;
      while(instrumentedClass(clazz)) {
        // If its a dynamic proxy, get super class until its a POJO
        clazz = (Class<?>) clazz.getSuperclass();
      }
      return clazz;
    }

    private Class<?> uninstrumentedClass(final Object object) {
      Class<?> uninstrumentedClass = (Class<?>) uninstrumentedClass(object.getClass());
      return uninstrumentedClass;
    }

    private static boolean instrumentedClass(final Class<?> clazz) {
      final String asString = clazz.toString();
      return asString.contains("CGLIB") || asString.contains("javassist");
    }

    private List<?> copyCollection(final Collection<?> collection) {
      // Why handle empty case separately? Worried there is some oddity with hibernate
      // that caused case to be added.
      if(collection.isEmpty()) {
        return Lists.newArrayList();
      }
      final List<Object> associatedObjectsCopy = Lists.newArrayList();
      for(final Object object : collection) {
        associatedObjectsCopy.add(copy(object));
      }
      return associatedObjectsCopy;
    }
  }

}
