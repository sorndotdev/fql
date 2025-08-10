package dev.sorn.fql.api;

import java.util.Objects;
import java.util.Optional;
import static dev.sorn.fql.api.Checks.checkNotNull;
import static dev.sorn.fql.api.FiscalPeriod.fiscalPeriod;
import static dev.sorn.fql.api.Optionality.empty;
import static dev.sorn.fql.api.Optionality.optional;

public class FiscalPeriodRange implements ValueObject<String> {
    public static final String SEPARATOR = "-";
    public static final FiscalPeriodRange EMPTY_FISCAL_PERIOD_RANGE = new FiscalPeriodRange(empty(), empty());
    protected Optional<FiscalPeriod> startInclusive;
    protected Optional<FiscalPeriod> endInclusive;

    protected FiscalPeriodRange(Optional<FiscalPeriod> startInclusive, Optional<FiscalPeriod> endInclusive) {
        checkNotNull("startInclusive", startInclusive);
        checkNotNull("endInclusive", endInclusive);
        if (startInclusive.isPresent() && endInclusive.isPresent()) {
            if (startInclusive.get().compareTo(endInclusive.get()) > 0) {
                throw new FQLError("'%s' is after '%s': %s < %s", "fiscalPeriodRange.startInclusive", "fiscalPeriodRange.endInclusive", startInclusive.get(), endInclusive.get());
            }
        }
        this.startInclusive = startInclusive;
        this.endInclusive = endInclusive;
    }

    public static FiscalPeriodRange fiscalPeriodRange(String value) {
        String[] parts = value.split(SEPARATOR, -1); // keep trailing empty parts
        String start = parts.length > 0 ? parts[0].trim() : "";
        String end = parts.length > 1 ? parts[1].trim() : "";
        return fiscalPeriodRange(
            start.isEmpty() ? empty() : optional(fiscalPeriod(start)),
            end.isEmpty() ? empty() : optional(fiscalPeriod(end))
        );
    }

    public static FiscalPeriodRange fiscalPeriodRange(Optional<FiscalPeriod> startInclusive, Optional<FiscalPeriod> endInclusive) {
        return new FiscalPeriodRange(startInclusive, endInclusive);
    }

    public Optional<FiscalPeriod> startInclusive() {
        return startInclusive;
    }

    public Optional<FiscalPeriod> endInclusive() {
        return endInclusive;
    }

    @Override
    public String value() {
        if (startInclusive.isPresent() && endInclusive.isPresent()) {
            return startInclusive.get() + SEPARATOR + endInclusive.get();
        }
        if (startInclusive.isPresent()) {
            return startInclusive.get() + SEPARATOR;
        }
        if (endInclusive.isPresent()) {
            return SEPARATOR + endInclusive.get();
        }
        return SEPARATOR;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof FiscalPeriodRange that)) return false;
        if (!Objects.equals(this.startInclusive, that.startInclusive)) return false;
        if (!Objects.equals(this.endInclusive, that.endInclusive)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = startInclusive.hashCode();
        result = 31 * result + endInclusive.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return value();
    }
}