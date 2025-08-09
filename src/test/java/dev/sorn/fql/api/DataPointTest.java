package dev.sorn.fql.api;

import dev.sorn.fql.DataPointTestData;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static dev.sorn.fql.api.FiscalPeriod.fiscalPeriod;
import static dev.sorn.fql.api.Instrument.instrument;
import static dev.sorn.fql.api.Metric.metric;
import static dev.sorn.fql.api.Scale.scale;
import static dev.sorn.fql.api.Source.source;
import static dev.sorn.fql.api.Unit.unit;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DataPointTest implements DataPointTestData {
    @Test
    void dataPoint_builds() {
        // given
        Instrument instrument = instrument("AAPL");
        Metric metric = metric("REVENUE");
        FiscalPeriod fiscalPeriod = fiscalPeriod("2024Q4");
        Unit unit = unit("USD");
        Scale scale = scale(1_000_000);
        Source source = source("SEEKING_ALPHA");
        int value = 124300;

        // when
        DataPoint<Integer> dp = DataPoint.Builder.<Integer>dataPoint()
            .instrument(instrument)
            .metric(metric)
            .fiscalPeriod(fiscalPeriod)
            .unit(unit)
            .scale(scale)
            .source(source)
            .value(value)
            .build();

        // then
        assertEquals(instrument, dp.instrument());
        assertEquals(metric, dp.metric());
        assertEquals(fiscalPeriod, dp.fiscalPeriod());
        assertEquals(unit, dp.unit());
        assertEquals(scale, dp.scale());
        assertEquals(source, dp.source());
        assertEquals(value, dp.value());
    }

    @Test
    void aByteDataPoint_builds() {
        // given
        byte b = Byte.MAX_VALUE;

        // when
        DataPoint<Byte> dp = aByteDataPoint(b);

        // then
        assertEquals(b, dp.value());
    }

    @Test
    void aShortDataPoint_builds() {
        // given
        short s = Short.MAX_VALUE;

        // when
        DataPoint<Short> dp = aShortDataPoint(s);

        // then
        assertEquals(s, dp.value());
    }

    @Test
    void anIntDataPoint_builds() {
        // given
        int i = Integer.MAX_VALUE;

        // when
        DataPoint<Integer> dp = anIntDataPoint(i);

        // then
        assertEquals(i, dp.value());
    }

    @Test
    void aLongDataPoint_builds() {
        // given
        long l = Long.MAX_VALUE;

        // when
        DataPoint<Long> dp = aLongDataPoint(l);

        // then
        assertEquals(l, dp.value());
    }

    @Test
    void aFloatDataPoint_builds() {
        // given
        float f = Float.MAX_VALUE;

        // when
        DataPoint<Float> dp = aFloatDataPoint(f);

        // then
        assertEquals(f, dp.value());
    }

    @Test
    void aDoubleDataPoint_builds() {
        // given
        double d = Double.MAX_VALUE;

        // when
        DataPoint<Double> dp = aDoubleDataPoint(d);

        // then
        assertEquals(d, dp.value());
    }

    @Test
    void aBigDecimalDataPoint_builds() {
        // given
        BigDecimal bd = new BigDecimal("1.0E309");

        // when
        DataPoint<BigDecimal> dp = aBigDecimalDataPoint(bd);

        // then
        assertEquals(bd, dp.value());
    }

    @Test
    void aRandomByteDataPoint_builds() {
        // when
        DataPoint<Byte> dp = aRandomByteDataPoint();

        // then
        assertInstanceOf(Byte.class, dp.value());
    }

    @Test
    void aRandomShortDataPoint_builds() {
        // when
        DataPoint<Short> dp = aRandomShortDataPoint();

        // then
        assertInstanceOf(Short.class, dp.value());
    }

    @Test
    void aRandomIntDataPoint_builds() {
        // when
        DataPoint<Integer> dp = aRandomIntDataPoint();

        // then
        assertInstanceOf(Integer.class, dp.value());
    }

    @Test
    void aRandomLongDataPoint_builds() {
        // when
        DataPoint<Long> dp = aRandomLongDataPoint();

        // then
        assertInstanceOf(Long.class, dp.value());
    }

    @Test
    void aRandomFloatDataPoint_builds() {
        // when
        DataPoint<Float> dp = aRandomFloatDataPoint();

        // then
        assertInstanceOf(Float.class, dp.value());
    }

    @Test
    void aRandomDoubleDataPoint_builds() {
        // when
        DataPoint<Double> dp = aRandomDoubleDataPoint();

        // then
        assertInstanceOf(Double.class, dp.value());
    }

    @Test
    void aRandomBigDecimalDataPoint_builds() {
        // when
        DataPoint<BigDecimal> dp = aRandomBigDecimalDataPoint();

        // then
        assertInstanceOf(BigDecimal.class, dp.value());
    }

    @Test
    void dataPoint_missing_instrument_throws() {
        // given
        DataPoint.Builder<Byte> builder = aRandomDataPointBuilder();
        builder.instrument(null);
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
        assertEquals("'dataPoint.instrument' is required", expected.getMessage());
    }

    @Test
    void dataPoint_missing_metric_throws() {
        // given
        DataPoint.Builder<Byte> builder = aRandomDataPointBuilder();
        builder.metric(null);
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
        assertEquals("'dataPoint.metric' is required", expected.getMessage());
    }

    @Test
    void dataPoint_missing_fiscalPeriod_throws() {
        // given
        DataPoint.Builder<Byte> builder = aRandomDataPointBuilder();
        builder.fiscalPeriod(null);
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
        assertEquals("'dataPoint.fiscalPeriod' is required", expected.getMessage());
    }

    @Test
    void dataPoint_missing_unit_throws() {
        // given
        DataPoint.Builder<Byte> builder = aRandomDataPointBuilder();
        builder.unit(null);
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
        assertEquals("'dataPoint.unit' is required", expected.getMessage());
    }

    @Test
    void dataPoint_missing_scale_throws() {
        // given
        DataPoint.Builder<Byte> builder = aRandomDataPointBuilder();
        builder.scale(null);
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
        assertEquals("'dataPoint.scale' is required", expected.getMessage());
    }

    @Test
    void dataPoint_missing_source_throws() {
        // given
        DataPoint.Builder<Byte> builder = aRandomDataPointBuilder();
        builder.source(null);
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
        assertEquals("'dataPoint.source' is required", expected.getMessage());
    }

    @Test
    void equals_same_true() {
        // given
        DataPoint<Byte> dp1 = aRandomByteDataPoint();

        // when
        boolean equal = dp1.equals(dp1);

        // then
        assertTrue(equal, format("expected: %s == %s", dp1, dp1));
    }

    @Test
    void equals_identical_true() {
        // given
        DataPoint<Integer> dp1 = aRandomIntDataPoint();
        DataPoint<Integer> dp2 = dp1.clone();

        // when
        boolean equal = dp1.equals(dp2);

        // then
        assertTrue(equal, format("expected: %s == %s", dp1, dp2));
    }

    @Test
    void equals_different_value_false() {
        // given
        DataPoint.Builder<Integer> builder = aRandomDataPointBuilder();
        DataPoint<Integer> dp1 = builder.value(1).value(1000).build();
        DataPoint<Integer> dp2 = builder.value(1).value(1001).build();

        // when
        boolean equal = dp1.equals(dp2);

        // then
        assertFalse(equal, format("expected: %s != %s", dp1, dp2));
    }

    @Test
    void equals_different_instrument_false() {
        // given
        DataPoint.Builder<Integer> builder = aRandomDataPointBuilder();
        DataPoint<Integer> dp1 = builder.value(1).instrument(instrument("AAPL")).build();
        DataPoint<Integer> dp2 = builder.value(1).instrument(instrument("MSFT")).build();

        // when
        boolean equal = dp1.equals(dp2);

        // then
        assertFalse(equal, format("expected: %s != %s", dp1, dp2));
    }

    @Test
    void equals_different_metric_false() {
        // given
        DataPoint.Builder<Integer> builder = aRandomDataPointBuilder();
        DataPoint<Integer> dp1 = builder.value(1).metric(metric("REVENUE")).build();
        DataPoint<Integer> dp2 = builder.value(1).metric(metric("ASSETS")).build();

        // when
        boolean equal = dp1.equals(dp2);

        // then
        assertFalse(equal, format("expected: %s != %s", dp1, dp2));
    }

    @Test
    void equals_different_fiscalPeriod_false() {
        // given
        DataPoint.Builder<Integer> builder = aRandomDataPointBuilder();
        DataPoint<Integer> dp1 = builder.value(1).fiscalPeriod(fiscalPeriod("1999")).build();
        DataPoint<Integer> dp2 = builder.value(1).fiscalPeriod(fiscalPeriod("2000")).build();

        // when
        boolean equal = dp1.equals(dp2);

        // then
        assertFalse(equal, format("expected: %s != %s", dp1, dp2));
    }

    @Test
    void equals_different_unit_false() {
        // given
        DataPoint.Builder<Integer> builder = aRandomDataPointBuilder();
        DataPoint<Integer> dp1 = builder.value(1).unit(unit("EUR")).build();
        DataPoint<Integer> dp2 = builder.value(1).unit(unit("USD")).build();

        // when
        boolean equal = dp1.equals(dp2);

        // then
        assertFalse(equal, format("expected: %s != %s", dp1, dp2));
    }

    @Test
    void equals_different_scale_false() {
        // given
        DataPoint.Builder<Integer> builder = aRandomDataPointBuilder();
        DataPoint<Integer> dp1 = builder.value(1).scale(scale(1_000)).build();
        DataPoint<Integer> dp2 = builder.value(1).scale(scale(1_000_000)).build();

        // when
        boolean equal = dp1.equals(dp2);

        // then
        assertFalse(equal, format("expected: %s != %s", dp1, dp2));
    }

    @Test
    void equals_different_source_false() {
        // given
        DataPoint.Builder<Integer> builder = aRandomDataPointBuilder();
        DataPoint<Integer> dp1 = builder.value(1).source(source("BLOOMBERG")).build();
        DataPoint<Integer> dp2 = builder.value(1).source(source("REFINITIV")).build();

        // when
        boolean equal = dp1.equals(dp2);

        // then
        assertFalse(equal, format("expected: %s != %s", dp1, dp2));
    }

    @Test
    void equals_different_instance_false() {
        // given
        DataPoint<Integer> dp1 = aRandomIntDataPoint();
        Object dp2 = new Object();

        // when
        boolean equal = dp1.equals(dp2);

        // then
        assertFalse(equal, format("expected: %s != %s", dp1, dp2));
    }

    @Test
    void equals_null_false() {
        // given
        DataPoint<Integer> dp1 = anIntDataPoint(1000);
        DataPoint<Integer> dp2 = null;

        // when
        boolean equal = dp1.equals(dp2);

        // then
        assertFalse(equal, format("expected: %s != %s", dp1, dp2));
    }

    @Test
    void toString_default_format() {
        // given
        Instrument instrument = instrument("AAPL");
        Metric metric = metric("REVENUE");
        FiscalPeriod fiscalPeriod = fiscalPeriod("2024Q4");
        Unit unit = unit("USD");
        Scale scale = scale(1_000_000);
        Source source = source("SEEKING_ALPHA");
        int value = 124300;

        // when
        DataPoint<Integer> dp = DataPoint.Builder.<Integer>dataPoint()
            .instrument(instrument)
            .metric(metric)
            .fiscalPeriod(fiscalPeriod)
            .unit(unit)
            .scale(scale)
            .source(source)
            .value(value)
            .build();

        // then
        assertEquals("DataPoint[instrument=AAPL, metric=REVENUE, fiscalPeriod=2024Q4, unit=USD, scale=1000000, source=SEEKING_ALPHA, value=124300]", dp.toString(), "bad string representation: " + dp);
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
        FiscalPeriod fiscalPeriod = fiscalPeriod("2024Q4");
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
        int value = 124300;

        // when
        DataPoint<Integer> dp = DataPoint.Builder.<Integer>dataPoint()
            .instrument(instrument)
            .metric(metric)
            .fiscalPeriod(fiscalPeriod)
            .unit(unit)
            .scale(scale)
            .source(source)
            .value(value)
            .build();

        // then
        String expected = "DataPoint[instrument=_AAPL, metric=_REV, fiscalPeriod=2024Q4, unit=_USD, scale=_MILLIONS, source=_SA, value=124300]";
        assertEquals(expected, dp.toString(), "toString() should respect interface overrides");
    }

    @Test
    void hashCode_consistency() {
        // given
        DataPoint<Integer> dp1 = aRandomIntDataPoint();
        DataPoint<Integer> dp2 = dp1.clone();

        // when
        int hc1 = dp1.hashCode();
        int hc2 = dp2.hashCode();

        // then
        assertEquals(hc1, hc2);
    }
}