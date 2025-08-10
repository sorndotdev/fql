package dev.sorn.fql.api;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.regex.Pattern;
import static dev.sorn.fql.api.Checks.checkInstanceOf;
import static dev.sorn.fql.api.Checks.checkMatches;
import static dev.sorn.fql.api.Checks.checkMax;
import static dev.sorn.fql.api.Checks.checkMin;
import static dev.sorn.fql.api.Checks.checkNotNull;
import static java.math.MathContext.DECIMAL32;

public final class Percentage implements DataPointValue {
    public static final Percentage MIN_VALUE = new Percentage(new BigDecimal("-1E7"), false);
    public static final Percentage MAX_VALUE = new Percentage(new BigDecimal("1E7"), false);
    public static final Percentage ZERO = new Percentage(BigDecimal.ZERO, false);
    public static final Percentage ONE = new Percentage(BigDecimal.ONE, false);
    public static final Percentage HUNDRED = new Percentage(BigDecimal.valueOf(100), false);
    public static final Pattern PATTERN = Pattern.compile("^-?(\\d+(\\.\\d+)?|\\.\\d+)%?$");
    private final BigDecimal value;

    private Percentage(BigDecimal value) {
        this(value, true);
    }

    private Percentage(BigDecimal value, boolean validate) {
        if (validate) {
            checkNotNull("percentage.value", value);
            checkMin("percentage.value", MIN_VALUE.value(), value);
            checkMax("percentage.value", MAX_VALUE.value(), value);
        }
        this.value = value;
    }

    public static Percentage pct(double value) {
        return percentage(value);
    }

    public static Percentage pct(String value) {
        return percentage(value);
    }

    public static Percentage percentage(double value) {
        return new Percentage(BigDecimal.valueOf(value));
    }

    public static Percentage percentage(String value) {
        checkNotNull("percentage.value", value);
        String normalized = value.strip();
        boolean negative = false;
        if (normalized.startsWith("-")) {
            negative = true;
            normalized = normalized.substring(1);
        }
        checkMatches("percentage.value", PATTERN, normalized);
        BigDecimal decimalValue;
        if (normalized.endsWith("%")) {
            normalized = normalized.substring(0, normalized.length() - 1); // remove '%'
            decimalValue = new BigDecimal(normalized).divide(BigDecimal.valueOf(100), DECIMAL32);
        } else {
            decimalValue = new BigDecimal(normalized);
        }
        if (negative) {
            decimalValue = decimalValue.negate();
        }
        return new Percentage(decimalValue);
    }

    @Override
    public Percentage add(DataPointValue that) {
        Percentage pct = checkInstanceOf("percentage.add", Percentage.class, that);
        return new Percentage(this.value.add(pct.value));
    }

    @Override
    public Percentage sub(DataPointValue that) {
        Percentage pct = checkInstanceOf("percentage.sub", Percentage.class, that);
        return new Percentage(this.value.subtract(pct.value));
    }

    @Override
    public Percentage mul(DataPointValue that) {
        Percentage pct = checkInstanceOf("percentage.mul", Percentage.class, that);
        return new Percentage(this.value.multiply(pct.value));
    }

    @Override
    public Percentage div(DataPointValue that) {
        Percentage pct = checkInstanceOf("percentage.div", Percentage.class, that);
        return new Percentage(this.value.divide(pct.value, DECIMAL32));
    }

    @Override
    public Percentage neg() {
        return new Percentage(this.value.negate());
    }

    @Override
    public BigDecimal value() {
        return value;
    }

    public double doubleValue() {
        return value.doubleValue();
    }

    @Override
    public int compareTo(DataPointValue that) {
        Percentage pct = checkInstanceOf("percentage.compareTo", Percentage.class, that);
        return this.value.compareTo(pct.value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Percentage that)) return false;
        if (!Objects.equals(this.value, that.value)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value().movePointRight(2).stripTrailingZeros().toPlainString() + "%";
    }
}