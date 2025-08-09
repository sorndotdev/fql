package dev.sorn.fql.api;

import java.util.Objects;
import java.util.regex.Pattern;
import static dev.sorn.fql.api.Checks.checkMax;
import static dev.sorn.fql.api.Checks.checkMin;
import static dev.sorn.fql.api.Checks.checkPattern;
import static dev.sorn.fql.api.Checks.checkPresent;
import static java.lang.Integer.parseInt;

public class FiscalYear {
    protected static final Pattern PATTERN = Pattern.compile("(\\d{4})");
    protected static final int MIN_YEAR = 1000;
    protected static final int MAX_YEAR = 9999;
    protected final int value;

    protected FiscalYear(int value) {
        checkMin("fiscalYear.value", MIN_YEAR, value);
        checkMax("fiscalYear.value", MAX_YEAR, value);
        this.value = value;
    }

    public static FiscalYear fiscalYear(int value) {
        return new FiscalYear(value);
    }

    public static FiscalYear fiscalYear(String value) {
        checkPresent("fiscalYear.value", value);
        checkPattern("fiscalYear.value", PATTERN, value);
        return fiscalYear(parseInt(value));
    }

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
}