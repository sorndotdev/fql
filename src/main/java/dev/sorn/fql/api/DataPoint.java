package dev.sorn.fql.api;

import java.util.Objects;
import static dev.sorn.fql.api.Checks.checkNotNull;

public class DataPoint {
    protected final Instrument instrument;
    protected final Metric metric;
    protected final FiscalPeriod fiscalPeriod;
    protected final Unit unit;
    protected final Scale scale;
    protected final Source source;
    protected final DataPointValue value;

    protected DataPoint(DataPoint.Builder builder) {
        this.instrument = checkNotNull("dataPoint.instrument", builder.instrument);
        this.metric = checkNotNull("dataPoint.metric", builder.metric);
        this.fiscalPeriod = checkNotNull("dataPoint.fiscalPeriod", builder.fiscalPeriod);
        this.unit = checkNotNull("dataPoint.unit", builder.unit);
        this.scale = checkNotNull("dataPoint.scale", builder.scale);
        this.source = checkNotNull("dataPoint.source", builder.source);
        this.value = checkNotNull("dataPoint.value", builder.value);
    }

    public final Instrument instrument() {
        return instrument;
    }

    public final Metric metric() {
        return metric;
    }

    public final FiscalPeriod fiscalPeriod() {
        return fiscalPeriod;
    }

    public final Unit unit() {
        return unit;
    }

    public final Scale scale() {
        return scale;
    }

    public final Source source() {
        return source;
    }

    public final DataPointValue value() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof DataPoint that)) return false;
        if (!instrument().equals(that.instrument())) return false;
        if (!metric().equals(that.metric())) return false;
        if (!fiscalPeriod().equals(that.fiscalPeriod())) return false;
        if (!unit().equals(that.unit())) return false;
        if (!scale().equals(that.scale())) return false;
        if (!source().equals(that.source())) return false;
        if (!value().equals(that.value())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(instrument, metric, fiscalPeriod, unit, scale, source, value);
    }

    @SuppressWarnings("all")
    @Override
    protected DataPoint clone() {
        return Builder.dataPoint()
            .instrument(instrument)
            .metric(metric)
            .fiscalPeriod(fiscalPeriod)
            .unit(unit)
            .scale(scale)
            .source(source)
            .value(value)
            .build();
    }

    @Override
    public String toString() {
        return String.format(
            "DataPoint[instrument=%s, metric=%s, fiscalPeriod=%s, unit=%s, scale=%s, source=%s, value=%s]",
            instrument._toString(),
            metric._toString(),
            fiscalPeriod._toString(),
            unit._toString(),
            scale._toString(),
            source._toString(),
            value
        );
    }

    public static class Builder {
        protected Instrument instrument;
        protected Metric metric;
        protected FiscalPeriod fiscalPeriod;
        protected Unit unit;
        protected Scale scale;
        protected Source source;
        protected DataPointValue value;

        public static Builder dataPoint() {
            return new Builder();
        }

        public final Builder instrument(Instrument instrument) {
            this.instrument = instrument;
            return this;
        }

        public final Builder metric(Metric metric) {
            this.metric = metric;
            return this;
        }

        public final Builder fiscalPeriod(FiscalPeriod fiscalPeriod) {
            this.fiscalPeriod = fiscalPeriod;
            return this;
        }

        public final Builder unit(Unit unit) {
            this.unit = unit;
            return this;
        }

        public final Builder scale(Scale scale) {
            this.scale = scale;
            return this;
        }

        public final Builder source(Source source) {
            this.source = source;
            return this;
        }

        public final Builder value(DataPointValue value) {
            this.value = value;
            return this;
        }

        public DataPoint build() {
            return new DataPoint(this);
        }
    }
}