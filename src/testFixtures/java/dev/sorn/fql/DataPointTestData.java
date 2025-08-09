package dev.sorn.fql;

import dev.sorn.fql.api.DataPoint;
import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

public interface DataPointTestData extends InstrumentTestData, MetricTestData, FiscalPeriodTestData, UnitTestData, ScaleTestData, SourceTestData {
    default DataPoint<Byte> aByteDataPoint(byte b) {
        DataPoint.Builder<Byte> builder = aRandomDataPointBuilder();
        return builder.value(b).build();
    }

    default DataPoint<Byte> aRandomByteDataPoint() {
        byte b = (byte) ThreadLocalRandom.current().nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE + 1);
        return aByteDataPoint(b);
    }

    default DataPoint<Short> aShortDataPoint(short s) {
        DataPoint.Builder<Short> builder = aRandomDataPointBuilder();
        return builder.value(s).build();
    }

    default DataPoint<Short> aRandomShortDataPoint() {
        short s = (short) ThreadLocalRandom.current().nextInt(Short.MIN_VALUE, Short.MAX_VALUE + 1);
        return aShortDataPoint(s);
    }

    default DataPoint<Integer> anIntDataPoint(int i) {
        DataPoint.Builder<Integer> builder = aRandomDataPointBuilder();
        return builder.value(i).build();
    }

    default DataPoint<Integer> aRandomIntDataPoint() {
        int i = ThreadLocalRandom.current().nextInt();
        return anIntDataPoint(i);
    }

    default DataPoint<Long> aLongDataPoint(long l) {
        DataPoint.Builder<Long> builder = aRandomDataPointBuilder();
        return builder.value(l).build();
    }

    default DataPoint<Long> aRandomLongDataPoint() {
        long l = ThreadLocalRandom.current().nextLong();
        return aLongDataPoint(l);
    }

    default DataPoint<Float> aFloatDataPoint(float f) {
        DataPoint.Builder<Float> builder = aRandomDataPointBuilder();
        return builder.value(f).build();
    }

    default DataPoint<Float> aRandomFloatDataPoint() {
        float f = ThreadLocalRandom.current().nextFloat();
        return aFloatDataPoint(f);
    }

    default DataPoint<Double> aDoubleDataPoint(double d) {
        DataPoint.Builder<Double> builder = aRandomDataPointBuilder();
        return builder.value(d).build();
    }

    default DataPoint<Double> aRandomDoubleDataPoint() {
        double d = ThreadLocalRandom.current().nextDouble();
        return aDoubleDataPoint(d);
    }

    default DataPoint<BigDecimal> aBigDecimalDataPoint(BigDecimal bd) {
        DataPoint.Builder<BigDecimal> builder = aRandomDataPointBuilder();
        return builder.value(bd).build();
    }

    default DataPoint<BigDecimal> aRandomBigDecimalDataPoint() {
        double randomDouble = ThreadLocalRandom.current().nextDouble();
        BigDecimal bd = BigDecimal.valueOf(randomDouble);
        return aBigDecimalDataPoint(bd);
    }

    default <T extends Comparable<T>> DataPoint.Builder<T> aRandomDataPointBuilder() {
        return DataPoint.Builder.<T>dataPoint()
            .instrument(aRandomInstrument())
            .metric(aRandomMetric())
            .fiscalPeriod(aRandomFiscalPeriod())
            .unit(aRandomUnit())
            .scale(aRandomScale())
            .source(aRandomSource());
    }
}