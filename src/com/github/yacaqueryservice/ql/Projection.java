package com.github.yacaqueryservice.ql;

import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Subclasses define abstractions over projections (i.e. expression appearing
 * in select lists).
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
@XmlSeeAlso({DistinctProjection.class,
             ColumnProjection.class,
             CountProjection.class,
             RowCountProjection.class})
public abstract class Projection implements HasHql {

}
