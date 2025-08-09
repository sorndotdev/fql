package dev.sorn.fql.api;

import dev.sorn.fql.MetricTestData;
import org.junit.jupiter.api.Test;
import static dev.sorn.fql.api.Metric.metric;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MetricTest implements MetricTestData {
    @Test
    void metric_value() {
        Metric metric = metric("REVENUE");
        assertEquals("REVENUE", metric.value());
    }

    @Test
    void metric_random_value() {
        Metric metric = aRandomMetric();
        assertNotNull(metric.value());
    }

    @Test
    void metric_is_interface() {
        assertTrue(Metric.class.isInterface(), "Metric should be an interface");
    }

    @Test
    void metric_functional_interface_annotation() {
        boolean isAnnotated = Metric.class.isAnnotationPresent(FunctionalInterface.class);
        assertTrue(isAnnotated, "Metric should be annotated with @FunctionalInterface");
    }

    @Test
    void metric_implements_value_object() {
        Metric metric = metric("REVENUE");
        assertTrue(metric instanceof ValueObject);
    }
}