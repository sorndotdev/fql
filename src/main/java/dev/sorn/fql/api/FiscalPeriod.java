package dev.sorn.fql.api;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static dev.sorn.fql.api.Checks.checkNotNull;
import static dev.sorn.fql.api.Optionality.optional;
import static dev.sorn.fql.api.Optionality.optionalComparator;
import static java.lang.Integer.parseInt;
import static java.util.Optional.empty;

public class FiscalPeriod implements Comparable<FiscalPeriod>, ValueObject<String> {
    protected static final Pattern YEAR_PATTERN = FiscalYear.PATTERN;
    protected static final Pattern YEAR_QUARTER_PATTERN = Pattern.compile(FiscalYear.PATTERN.pattern() + FiscalQuarter.PATTERN.pattern());
    protected final FiscalYear year;
    protected final Optional<FiscalQuarter> quarter;

    protected FiscalPeriod(FiscalYear year, Optional<FiscalQuarter> quarter) {
        checkNotNull("fiscalPeriod.year", year);
        checkNotNull("fiscalPeriod.quarter", quarter);
        this.year = year;
        this.quarter = quarter;
    }

    public static FiscalPeriod fiscalPeriod(String value) throws FQLError {
        checkNotNull("fiscalPeriod.value", value);
        Matcher m;

        m = YEAR_PATTERN.matcher(value);
        if (m.matches()) {
            int year = parseInt(m.group(1));
            return fiscalPeriod(FiscalYear.fiscalYear(year));
        }

        m = YEAR_QUARTER_PATTERN.matcher(value);
        if (m.matches()) {
            int year = parseInt(m.group(1));
            int quarter = parseInt(m.group(2));
            return fiscalPeriod(
                FiscalYear.fiscalYear(year),
                optional(FiscalQuarter.fiscalQuarter(quarter))
            );
        }

        throw new FQLError("invalid fiscal period format: " + value);
    }

    public static FiscalPeriod fiscalPeriod(FiscalYear year) {
        return new FiscalPeriod(year, empty());
    }

    public static FiscalPeriod fiscalPeriod(FiscalYear year, Optional<FiscalQuarter> quarter) {
        return new FiscalPeriod(year, quarter);
    }

    public FiscalYear fiscalYear() {
        return year;
    }

    public Optional<FiscalQuarter> fiscalQuarter() {
        return quarter;
    }

    @Override
    public String value() {
        return quarter
            .map(fiscalQuarter -> String.format("%sQ%1d", year, fiscalQuarter.value()))
            .orElseGet(year::toString);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof FiscalPeriod that)) return false;
        if (!Objects.equals(this.toString(), that.toString())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = fiscalYear().hashCode();
        int q = quarter.map(FiscalQuarter::value).orElse(-1);
        result = 31 * result + q;
        return result;
    }

    @Override
    public String toString() {
        return value();
    }

    @Override
    public int compareTo(FiscalPeriod that) {
        return Comparator
            .comparing(FiscalPeriod::fiscalYear)
            .thenComparing(FiscalPeriod::fiscalQuarter, optionalComparator())
            .compare(this, that);
    }
}