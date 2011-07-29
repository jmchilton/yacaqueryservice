package com.github.yacaqueryservice.engine;

import gov.nih.nci.system.applicationservice.ApplicationException;
import gov.nih.nci.system.query.hibernate.HQLCriteria;

import java.util.List;

/**
 * Interface containing just the one definition from {@link gov.nih.nci.system.applicationservice.ApplicationService}
 * that is used by this package.
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
interface HibernateWrapper {

  
  <T> List<T> query(final HQLCriteria criteria) throws ApplicationException;
  
}
