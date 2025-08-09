package dev.sorn.fql.api;

import java.util.Objects;
import java.util.regex.Pattern;
import static dev.sorn.fql.api.Checks.checkMatches;
import static dev.sorn.fql.api.Checks.checkMax;
import static dev.sorn.fql.api.Checks.checkMin;
import static dev.sorn.fql.api.Checks.checkNotNull;
import static java.lang.Integer.parseInt;

public class FiscalYear implements Comparable<FiscalYear>, ValueObject<Integer> {
    protected static final Pattern PATTERN = Pattern.compile("(\\d{4})");
    protected static final int MIN_VALUE = 1000;
    protected static final int MAX_VALUE = 9999;
    protected final int value;

    protected FiscalYear(int value) {
        checkMin("fiscalYear.value", MIN_VALUE, value);
        checkMax("fiscalYear.value", MAX_VALUE, value);
        this.value = value;
    }

    public static FiscalYear fiscalYear(int value) {
        return new FiscalYear(value);
    }

    public static FiscalYear fiscalYear(String value) {
        checkNotNull("fiscalYear.value", value);
        checkMatches("fiscalYear.value", PATTERN, value);
        return fiscalYear(parseInt(value));
    }

    @Override
    public Integer value() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof FiscalYear that)) return false;
        if (!Objects.equals(this.value(), that.value())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public int compareTo(FiscalYear that) {
        return Integer.compare(this.value, that.value);
    }
}