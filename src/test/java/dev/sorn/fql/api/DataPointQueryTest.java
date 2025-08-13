package dev.sorn.fql.api;

import dev.sorn.fql.DataPointQueryTestData;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static dev.sorn.fql.api.DataPointQuery.Builder.dataPointQuery;
import static dev.sorn.fql.api.FiscalPeriodRange.fiscalPeriodRange;
import static dev.sorn.fql.api.Instrument.instrument;
import static dev.sorn.fql.api.Metric.metric;
import static dev.sorn.fql.api.Scale.scale;
import static dev.sorn.fql.api.Source.source;
import static dev.sorn.fql.api.Unit.unit;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DataPointQueryTest implements DataPointQueryTestData {
    @Test
    void dataPointQuery_builds() {
        // given
        FiscalPeriodRange fiscalPeriodRange = fiscalPeriodRange("2005Q1-");
        Set<Instrument> instruments = Set.of(instrument("AAPL"));
        Set<Metric> metrics = Set.of(metric("REVENUE"));
        Set<Unit> units = Set.of(unit("USD"));
        Set<Scale> scales = Set.of(scale(1_000_000));
        Set<Source> sources = Set.of(source("SEEKING_ALPHA"));

        // when
        DataPointQuery query = dataPointQuery()
            .fiscalPeriodRange(fiscalPeriodRange)
            .instruments(instruments)
            .metrics(metrics)
            .units(units)
            .scales(scales)
            .sources(sources)
            .build();

        // then
        assertEquals(fiscalPeriodRange, query.fiscalPeriodRange());
        assertIterableEquals(instruments, query.instruments());
        assertIterableEquals(metrics, query.metrics());
        assertIterableEquals(units, query.units());
        assertIterableEquals(scales, query.scales());
        assertIterableEquals(sources, query.sources());
    }

    @Test
    void dataPoint_missing_instruments_throws() {
        // given
        DataPointQuery.Builder builder = aRandomDataPointQueryBuilder().instruments(Set.of());
        Exception expected = null;

        // when
        try {
            builder.build();
        } catch (Exception e) {
            expected = e;
        }

        // then
        assertNotNull(expected, "exception not thrown");
        assertInstanceOf(FQLError.class, expected, "wrong exception instance");
        assertEquals("[DataPointQuery#<init>] '0' is below min '1'", expected.getMessage());
    }

    @Test
    void dataPoint_modifying_collections_throws() {
        // given
        DataPointQuery query = aRandomDataPointQueryBuilder().build();
        List<Exception> exceptions = new ArrayList<>();

        // when
        exceptions.addAll(List.of(
            assertThrows(UnsupportedOperationException.class, () -> query.instruments().add(aRandomInstrument())),
            assertThrows(UnsupportedOperationException.class, () -> query.metrics().add(aRandomMetric())),
            assertThrows(UnsupportedOperationException.class, () -> query.units().add(aRandomCurrency())),
            assertThrows(UnsupportedOperationException.class, () -> query.scales().add(aRandomScale())),
            assertThrows(UnsupportedOperationException.class, () -> query.sources().add(aRandomSource()))));

        // then
        assertEquals(5, exceptions.size());
        assertInstanceOf(UnsupportedOperationException.class, exceptions.get(0));
        assertInstanceOf(UnsupportedOperationException.class, exceptions.get(1));
        assertInstanceOf(UnsupportedOperationException.class, exceptions.get(2));
        assertInstanceOf(UnsupportedOperationException.class, exceptions.get(3));
        assertInstanceOf(UnsupportedOperationException.class, exceptions.get(4));
    }

    @Test
    void equals_same_true() {
        // given
        DataPointQuery query = aRandomDataPointQuery();

        // when
        boolean equal = query.equals(query);

        // then
        assertTrue(equal, format("expected: %s == %s", query, query));
    }

    @Test
    void equals_identical_true() {
        // given
        DataPointQuery dpq1 = aRandomDataPointQuery();
        DataPointQuery dpq2 = dpq1.clone();

        // when
        boolean equal = dpq1.equals(dpq2);

        // then
        assertTrue(equal, format("expected: %s == %s", dpq1, dpq2));
    }

    @Test
    void equals_different_fiscalPeriodRange_false() {
        // given
        DataPointQuery.Builder builder = aRandomDataPointQueryBuilder();
        DataPointQuery dpq1 = builder.fiscalPeriodRange(fiscalPeriodRange("2024Q1-")).build();
        DataPointQuery dpq2 = builder.fiscalPeriodRange(fiscalPeriodRange("2024Q2-")).build();

        // when
        boolean equal = dpq1.equals(dpq2);

        // then
        assertFalse(equal, format("expected: %s != %s", dpq1, dpq2));
    }

    @Test
    void equals_different_instruments_false() {
        // given
        DataPointQuery.Builder builder = aRandomDataPointQueryBuilder();
        DataPointQuery dpq1 = builder.instruments(Set.of(() -> "AAPL")).build();
        DataPointQuery dpq2 = builder.instruments(Set.of(() -> "MSFT")).build();

        // when
        boolean equal = dpq1.equals(dpq2);

        // then
        assertFalse(equal, format("expected: %s != %s", dpq1, dpq2));
    }

    @Test
    void equals_different_metrics_false() {
        // given
        DataPointQuery.Builder builder = aRandomDataPointQueryBuilder();
        DataPointQuery dpq1 = builder.metrics(Set.of(() -> "TOTAL_ASSETS")).build();
        DataPointQuery dpq2 = builder.metrics(Set.of(() -> "TOTAL_LIABILITIES")).build();

        // when
        boolean equal = dpq1.equals(dpq2);

        // then
        assertFalse(equal, format("expected: %s != %s", dpq1, dpq2));
    }

    @Test
    void equals_different_units_false() {
        // given
        DataPointQuery.Builder builder = aRandomDataPointQueryBuilder();
        DataPointQuery dpq1 = builder.units(Set.of(() -> "EUR")).build();
        DataPointQuery dpq2 = builder.units(Set.of(() -> "USD")).build();

        // when
        boolean equal = dpq1.equals(dpq2);

        // then
        assertFalse(equal, format("expected: %s != %s", dpq1, dpq2));
    }

    @Test
    void equals_different_scales_false() {
        // given
        DataPointQuery.Builder builder = aRandomDataPointQueryBuilder();
        DataPointQuery dpq1 = builder.scales(Set.of(() -> 1_000)).build();
        DataPointQuery dpq2 = builder.scales(Set.of(() -> 1_000_000)).build();

        // when
        boolean equal = dpq1.equals(dpq2);

        // then
        assertFalse(equal, format("expected: %s != %s", dpq1, dpq2));
    }

    @Test
    void equals_different_sources_false() {
        // given
        DataPointQuery.Builder builder = aRandomDataPointQueryBuilder();
        DataPointQuery dpq1 = builder.sources(Set.of(() -> "BLOOMBERG")).build();
        DataPointQuery dpq2 = builder.sources(Set.of(() -> "REFINITIV")).build();

        // when
        boolean equal = dpq1.equals(dpq2);

        // then
        assertFalse(equal, format("expected: %s != %s", dpq1, dpq2));
    }

    @Test
    void equals_different_instance_false() {
        // given
        DataPointQuery dpq1 = aRandomDataPointQuery();
        Object dpq2 = new Object();

        // when
        boolean equal = dpq1.equals(dpq2);

        // then
        assertFalse(equal, format("expected: %s != %s", dpq1, dpq2));
    }

    @Test
    void equals_null_false() {
        // given
        DataPointQuery dpq1 = aRandomDataPointQuery();
        DataPointQuery dpq2 = null;

        // when
        boolean equal = dpq1.equals(dpq2);

        // then
        assertFalse(equal, format("expected: %s != %s", dpq1, dpq2));
    }

    @Test
    void toString_default_format() {
        // given
        FiscalPeriodRange fiscalPeriodRange = fiscalPeriodRange("2024Q4-");
        Set<Instrument> instrument = Set.of(instrument("AAPL"));
        Set<Metric> metric = Set.of(metric("REVENUE"));
        Set<Unit> unit = Set.of(unit("USD"));
        Set<Scale> scale = Set.of(scale(1_000_000));
        Set<Source> source = Set.of(source("SEEKING_ALPHA"));

        // when
        DataPointQuery dpq = dataPointQuery()
            .fiscalPeriodRange(fiscalPeriodRange)
            .instruments(instrument)
            .metrics(metric)
            .units(unit)
            .scales(scale)
            .sources(source)
            .build();

        // then
        assertEquals("DataPointQuery[fiscalPeriodRange=2024Q4-, instruments=[AAPL], metrics=[REVENUE], units=[USD], scales=[1000000], sources=[SEEKING_ALPHA]]", dpq.toString(), "bad string representation: " + dpq);
    }

    @Test
    void toString_overrides_default_format() {
        // given
        Instrument instrument = new Instrument() {
            @Override
            public String value() {
                return "AAPL";
            }

            @Override
            public String toString() {
                return "_AAPL";
            }
        };
        Metric metric = new Metric() {
            @Override
            public String value() {
                return "REVENUE";
            }

            @Override
            public String toString() {
                return "_REV";
            }
        };
        Unit unit = new Unit() {
            @Override
            public String value() {
                return "USD";
            }

            @Override
            public String toString() {
                return "_USD";
            }
        };
        Scale scale = new Scale() {
            @Override
            public Integer value() {
                return 1_000_000;
            }

            @Override
            public String toString() {
                return "_MILLIONS";
            }
        };
        Source source = new Source() {
            @Override
            public String value() {
                return "SEEKING_ALPHA";
            }

            @Override
            public String toString() {
                return "_SA";
            }
        };

        // when
        DataPointQuery query = dataPointQuery()
            .fiscalPeriodRange(fiscalPeriodRange("2005Q1-2010Q1"))
            .instruments(Set.of(instrument))
            .metrics(Set.of(metric))
            .units(Set.of(unit))
            .scales(Set.of(scale))
            .sources(Set.of(source))
            .build();

        // then
        String expected = "DataPointQuery[fiscalPeriodRange=2005Q1-2010Q1, instruments=[_AAPL], metrics=[_REV], units=[_USD], scales=[_MILLIONS], sources=[_SA]]";
        assertEquals(expected, query.toString(), "toString() should respect interface overrides");
    }

    @Test
    void hashCode_consistency() {
        // given
        DataPointQuery dpq1 = aRandomDataPointQuery();
        DataPointQuery dpq2 = dpq1.clone();

        // when
        int hc1 = dpq1.hashCode();
        int hc2 = dpq2.hashCode();

        // then
        assertEquals(hc1, hc2);
    }
}