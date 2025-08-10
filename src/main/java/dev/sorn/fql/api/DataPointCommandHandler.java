package dev.sorn.fql.api;

import java.util.Collection;

@FunctionalInterface
public interface DataPointCommandHandler {
    Collection<DataPoint> saveDataPoints(Collection<DataPoint> dataPoints);
}