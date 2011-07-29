package com.github.yacaqueryservice.ql;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * This class contains utility methods for creating various projection objects
 * as defined by this package.
 * 
 * @author John Chilton (jmchilton at gmail dot com)
 *
 */
public class Projections {

  public static RowCountProjection rowCount() {
    return new RowCountProjection();
  }

  public static ColumnProjection forColumn(final ColumnExpression expression) {
    final ColumnProjection projection = new ColumnProjection();
    projection.setColumnExpression(expression);
    return projection;
  }

  public static CountProjection count(final ColumnExpression columnExpression, final boolean distinct) {
    final CountProjection projection = new CountProjection();
    projection.setColumnExpression(columnExpression);
    projection.setDistinct(distinct);
    return projection;
  }

  public static DistinctProjection distinct(final Projection projection) {
    final DistinctProjection distinctProjection = new DistinctProjection();
    distinctProjection.setProjection(projection);
    return distinctProjection;
  }

  public static List<Projection> forColumns(final List<ColumnExpression> columnExpressions) {
    final List<Projection> projections = Lists.newArrayList();
    for(ColumnExpression column : columnExpressions) {
      projections.add(Projections.forColumn(column));
    }
    return projections;
  }

}
