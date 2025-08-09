package dev.sorn.fql;

import dev.sorn.fql.api.Metric;

public interface MetricTestData {
    default Metric aRandomMetric() {
        return () -> "<METRIC>";
    }
}