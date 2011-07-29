package com.github.yacaqueryservice.engine;

interface BeanSanitizer {
  <T> T sanitize(T hibernateObject, Iterable<String> eagerAssociations);
}
