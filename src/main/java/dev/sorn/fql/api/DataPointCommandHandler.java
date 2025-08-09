package dev.sorn.fql.api;

import java.util.Collection;

@FunctionalInterface
public interface DataPointCommandHandler {
    <T extends Comparable<T>> Collection<DataPoint<T>> saveDataPoints(Collection<T> dataPoints);
}