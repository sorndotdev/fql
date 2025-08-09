package dev.sorn.fql.api;

import java.util.Objects;
import static dev.sorn.fql.api.Checks.checkPresent;

public class DataPoint<T extends Comparable<T>> {
    protected final Instrument instrument;
    protected final Metric metric;
    protected final FiscalPeriod fiscalPeriod;
    protected final Unit unit;
    protected final Scale scale;
    protected final Source source;
    protected final T value;

    protected DataPoint(
        Instrument instrument,
        Metric metric,
        FiscalPeriod fiscalPeriod,
        Unit unit,
        Scale scale,
        Source source,
        T value
    ) {
        this.instrument = checkPresent("dataPoint.instrument", instrument);
        this.metric = checkPresent("dataPoint.metric", metric);
        this.fiscalPeriod = checkPresent("dataPoint.fiscalPeriod", fiscalPeriod);
        this.unit = checkPresent("dataPoint.unit", unit);
        this.scale = checkPresent("dataPoint.scale", scale);
        this.source = checkPresent("dataPoint.source", source);
        this.value = checkPresent("dataPoint.value", value);
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

    public final T value() {
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
    protected DataPoint<T> clone() {
        return new DataPoint<>(
            instrument,
            metric,
            fiscalPeriod,
            unit,
            scale,
            source,
            value
        );
    }

    @Override
    public String toString() {
        return String.format(
            "DataPoint[instrument=%s, metric=%s, fiscalPeriod=%s, unit=%s, scale=%s, source=%s, value=%s]",
            safeToString(instrument),
            safeToString(metric),
            safeToString(fiscalPeriod),
            safeToString(unit),
            safeToString(scale),
            safeToString(source),
            value
        );
    }

    private static String safeToString(Object obj) {
        String str = obj.toString();
        // crude heuristic: if toString() looks like default Object toString() (class@hashcode)
        if (str.matches(".*@\\p{XDigit}+$")) {
            // fallback to value() if available via an interface, else return str
            if (obj instanceof Instrument i) return i.value();
            if (obj instanceof Metric m) return m.value();
            if (obj instanceof Unit u) return u.value();
            if (obj instanceof Scale s) return "" + s.value();
            if (obj instanceof Source so) return so.value();
        }
        return str;
    }

    public static class Builder<T extends Comparable<T>> {
        protected Instrument instrument;
        protected Metric metric;
        protected FiscalPeriod fiscalPeriod;
        protected Unit unit;
        protected Scale scale;
        protected Source source;
        protected T value;

        public static <T extends Comparable<T>> Builder<T> dataPoint() {
            return new Builder<>();
        }

        public final Builder<T> instrument(Instrument instrument) {
            this.instrument = instrument;
            return this;
        }

        public final Builder<T> metric(Metric metric) {
            this.metric = metric;
            return this;
        }

        public final Builder<T> fiscalPeriod(FiscalPeriod fiscalPeriod) {
            this.fiscalPeriod = fiscalPeriod;
            return this;
        }

        public final Builder<T> unit(Unit unit) {
            this.unit = unit;
            return this;
        }

        public final Builder<T> scale(Scale scale) {
            this.scale = scale;
            return this;
        }

        public final Builder<T> source(Source source) {
            this.source = source;
            return this;
        }

        public final Builder<T> value(T value) {
            this.value = value;
            return this;
        }

        public DataPoint<T> build() {
            return new DataPoint<>(instrument, metric, fiscalPeriod, unit, scale, source, value);
        }
    }
}