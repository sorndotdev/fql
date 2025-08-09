package dev.sorn.fql.api;

import java.util.Collection;

@FunctionalInterface
public interface DataPointQueryHandler {
    <T extends Comparable<T>> Collection<DataPoint<T>> findDataPoints(DataPointQuery query);
}