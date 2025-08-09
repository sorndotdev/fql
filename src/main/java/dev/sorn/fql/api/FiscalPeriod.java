package dev.sorn.fql.api;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static dev.sorn.fql.api.Checks.checkPresent;
import static java.lang.Integer.parseInt;
import static java.util.Optional.empty;

public class FiscalPeriod {
    protected static final Pattern YEAR_PATTERN = FiscalYear.PATTERN;
    protected static final Pattern YEAR_QUARTER_PATTERN = Pattern.compile(FiscalYear.PATTERN.pattern() + "-" + FiscalQuarter.PATTERN.pattern());
    protected final FiscalYear year;
    protected final Optional<FiscalQuarter> quarter;

    protected FiscalPeriod(FiscalYear year, Optional<FiscalQuarter> quarter) {
        checkPresent("fiscalPeriod.year", year);
        checkPresent("fiscalPeriod.quarter", quarter);
        this.year = year;
        this.quarter = quarter;
    }

    public static FiscalPeriod fiscalPeriod(String value) throws FQLError {
        checkPresent("fiscalPeriod.value", value);
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
                Optional.of(FiscalQuarter.fiscalQuarter(quarter))
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
        if (quarter.isPresent()) {
            return String.format("%s-Q%1d", year, quarter.get().value());
        }
        return year.toString();
    }
}