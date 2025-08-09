package dev.sorn.fql.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import static dev.sorn.fql.api.Checks.checkMin;
import static dev.sorn.fql.api.Checks.checkNotNull;
import static dev.sorn.fql.api.DataPointQuery.Builder.dataPointQuery;
import static dev.sorn.fql.api.FiscalPeriodRange.EMPTY_FISCAL_PERIOD_RANGE;
import static java.util.Collections.unmodifiableCollection;
import static java.util.Objects.hash;

public class DataPointQuery {
    protected final FiscalPeriodRange fiscalPeriodRange;
    protected final Collection<Instrument> instruments;
    protected final Collection<Metric> metrics;
    protected final Collection<Unit> units;
    protected final Collection<Scale> scales;
    protected final Collection<Source> sources;

    protected DataPointQuery(Builder builder) {
        checkNotNull("dataPointQuery.builder", builder);
        checkNotNull("dataPointQuery.fiscalPeriodRange", builder.fiscalPeriodRange);
        checkMin("dataPointQuery.instruments.size", 1, builder.instruments.size());
        this.fiscalPeriodRange = builder.fiscalPeriodRange;
        this.instruments = builder.instruments;
        this.metrics = builder.metrics;
        this.units = builder.units;
        this.scales = builder.scales;
        this.sources = builder.sources;
    }

    public final FiscalPeriodRange fiscalPeriodRange() {
        return fiscalPeriodRange;
    }

    public final Collection<Instrument> instruments() {
        return unmodifiableCollection(instruments);
    }

    public final Collection<Metric> metrics() {
        return unmodifiableCollection(metrics);
    }

    public final Collection<Unit> units() {
        return unmodifiableCollection(units);
    }

    public final Collection<Scale> scales() {
        return unmodifiableCollection(scales);
    }

    public final Collection<Source> sources() {
        return unmodifiableCollection(sources);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof DataPointQuery that)) return false;
        if (!Objects.equals(fiscalPeriodRange, that.fiscalPeriodRange)) return false;
        if (!Objects.equals(instruments, that.instruments)) return false;
        if (!Objects.equals(metrics, that.metrics)) return false;
        if (!Objects.equals(units, that.units)) return false;
        if (!Objects.equals(scales, that.scales)) return false;
        if (!Objects.equals(sources, that.sources)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return hash(fiscalPeriodRange, instruments, metrics, units, scales, sources);
    }

    @SuppressWarnings("all")
    @Override
    protected DataPointQuery clone() {
        return new DataPointQuery(dataPointQuery()
            .fiscalPeriodRange(fiscalPeriodRange)
            .instruments(instruments)
            .metrics(metrics)
            .units(units)
            .scales(scales)
            .sources(sources));
    }

    @Override
    public String toString() {
        return String.format(
            "DataPointQuery[fiscalPeriodRange=%s, instruments=%s, metrics=%s, units=%s, scales=%s, sources=%s]",
            fiscalPeriodRange,
            instruments.stream().map(ValueObject::_toString).collect(Collectors.toSet()),
            metrics.stream().map(ValueObject::_toString).collect(Collectors.toSet()),
            units.stream().map(ValueObject::_toString).collect(Collectors.toSet()),
            scales.stream().map(ValueObject::_toString).collect(Collectors.toSet()),
            sources.stream().map(ValueObject::_toString).collect(Collectors.toSet())
        );
    }

    public static class Builder {
        protected FiscalPeriodRange fiscalPeriodRange = EMPTY_FISCAL_PERIOD_RANGE;
        protected Collection<Instrument> instruments = new ArrayList<>();
        protected Collection<Metric> metrics = new ArrayList<>();
        protected Collection<Unit> units = new ArrayList<>();
        protected Collection<Scale> scales = new ArrayList<>();
        protected Collection<Source> sources = new ArrayList<>();

        public static Builder dataPointQuery() {
            return new Builder();
        }

        public Builder fiscalPeriodRange(FiscalPeriodRange fiscalPeriodRange) {
            this.fiscalPeriodRange = fiscalPeriodRange;
            return this;
        }

        public Builder instruments(Collection<Instrument> instruments) {
            this.instruments = instruments;
            return this;
        }

        public Builder metrics(Collection<Metric> metrics) {
            this.metrics = metrics;
            return this;
        }

        public Builder units(Collection<Unit> units) {
            this.units = units;
            return this;
        }

        public Builder scales(Collection<Scale> scales) {
            this.scales = scales;
            return this;
        }

        public Builder sources(Collection<Source> sources) {
            this.sources = sources;
            return this;
        }

        public DataPointQuery build() {
            return new DataPointQuery(this);
        }
    }
}