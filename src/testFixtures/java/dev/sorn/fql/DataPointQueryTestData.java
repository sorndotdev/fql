package dev.sorn.fql;

import dev.sorn.fql.api.DataPointQuery;
import java.util.Set;
import static dev.sorn.fql.api.DataPointQuery.Builder.dataPointQuery;

public interface DataPointQueryTestData extends InstrumentTestData, MetricTestData, FiscalPeriodRangeTestData, UnitTestData, ScaleTestData, SourceTestData {
    default DataPointQuery aRandomDataPointQuery() {
        return aRandomDataPointQueryBuilder().build();
    }

    default DataPointQuery.Builder aRandomDataPointQueryBuilder() {
        return dataPointQuery()
            .instruments(Set.of(aRandomInstrument(), aRandomInstrument()))
            .metrics(Set.of(aRandomMetric(), aRandomMetric()))
            .fiscalPeriodRange(aRandomFiscalPeriodRange())
            .units(Set.of(aRandomUnit()))
            .scales(Set.of(aRandomScale()))
            .sources(Set.of(aRandomSource()));
    }
}