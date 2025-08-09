package dev.sorn.fql.api;

import java.util.Objects;
import java.util.Optional;
import static dev.sorn.fql.api.Checks.checkNotNull;

public class FiscalPeriodRange {
    protected Optional<FiscalPeriod> startInclusive;
    protected Optional<FiscalPeriod> endInclusive;

    protected FiscalPeriodRange(Optional<FiscalPeriod> startInclusive, Optional<FiscalPeriod> endInclusive) {
        checkNotNull("fiscalPeriodRange.startInclusive", startInclusive);
        checkNotNull("fiscalPeriodRange.endInclusive", endInclusive);
        if (startInclusive.isPresent() && endInclusive.isPresent()) {
            if (startInclusive.get().compareTo(endInclusive.get()) > 0) {
                throw new FQLError("'%s' is after '%s': %s < %s", "fiscalPeriodRange.startInclusive", "fiscalPeriodRange.endInclusive", startInclusive.get(), endInclusive.get());
            }
        }
        this.startInclusive = startInclusive;
        this.endInclusive = endInclusive;
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
        if (startInclusive.isPresent() && endInclusive.isPresent()) {
            return startInclusive.get() + "-" + endInclusive.get();
        }
        if (startInclusive.isPresent()) {
            return startInclusive.get() + "-";
        }
        if (endInclusive.isPresent()) {
            return "-" + endInclusive.get();
        }
        return "-";
    }
}