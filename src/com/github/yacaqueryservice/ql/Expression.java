package com.github.yacaqueryservice.ql;

import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Base class for abstractions over various HQL expression
 * types.
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
@XmlSeeAlso({BinaryComparisonExpression.class,
             BetweenComparisonExpression.class,
             NullityCondition.class,
             InExpression.class,
             ConstantExpression.class,
             ColumnExpression.class,
             ClassIsExpression.class})
public abstract class Expression implements HasHql {

}
