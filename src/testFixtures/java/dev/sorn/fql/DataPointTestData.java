package dev.sorn.fql;

import dev.sorn.fql.api.DataPoint;
import dev.sorn.fql.api.Percentage;
import static dev.sorn.fql.api.DataPoint.Builder.dataPoint;

public interface DataPointTestData extends InstrumentTestData, MetricTestData, FiscalPeriodTestData, CurrencyTestData, ScaleTestData, SourceTestData, PercentageTestData {
    default DataPoint aRandomPercentageDataPoint() {
        return aRandomDataPointBuilder()
            .value(aRandomPercentage())
            .build();
    }

    default DataPoint aPercentageDataPoint(Percentage pct) {
        return aRandomDataPointBuilder()
            .value(pct)
            .build();
    }

    default DataPoint.Builder aRandomDataPointBuilder() {
        return dataPoint()
            .instrument(aRandomInstrument())
            .metric(aRandomMetric())
            .fiscalPeriod(aRandomFiscalPeriod())
            .unit(aRandomCurrency())
            .scale(aRandomScale())
            .source(aRandomSource());
    }
}