package dev.sorn.fql.api;

import java.util.Objects;
import java.util.regex.Pattern;
import static dev.sorn.fql.api.Checks.checkMax;
import static dev.sorn.fql.api.Checks.checkMin;
import static dev.sorn.fql.api.Checks.checkMatches;
import static dev.sorn.fql.api.Checks.checkPresent;
import static java.lang.Integer.parseInt;

public class FiscalQuarter {
    protected static final Pattern PATTERN = Pattern.compile("Q([1-4])");
    protected static final int MIN_QUARTER = 1;
    protected static final int MAX_QUARTER = 4;
    protected final int value;

    protected FiscalQuarter(int value) {
        checkMin("fiscalQuarter.value", MIN_QUARTER, value);
        checkMax("fiscalQuarter.value", MAX_QUARTER, value);
        this.value = value;
    }

    public static FiscalQuarter fiscalQuarter(int value) {
        return new FiscalQuarter(value);
    }

    public static FiscalQuarter fiscalQuarter(String value) {
        checkPresent("fiscalQuarter.value", value);
        checkMatches("fiscalQuarter.value", PATTERN, value);
        return fiscalQuarter(parseInt(value.substring(1)));
    }

    public Integer value() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof FiscalQuarter that)) return false;
        if (!Objects.equals(this.value(), that.value())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public String toString() {
        return "Q" + value;
    }
}