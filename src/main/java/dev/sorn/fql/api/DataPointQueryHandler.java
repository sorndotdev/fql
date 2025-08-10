package dev.sorn.fql.api;

import java.util.Collection;

@FunctionalInterface
public interface DataPointQueryHandler {
    Collection<DataPoint> findDataPoints(DataPointQuery query);
}