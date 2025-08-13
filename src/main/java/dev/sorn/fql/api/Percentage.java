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
import static java.math.RoundingMode.HALF_EVEN;

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
            checkNotNull("value", value);
            checkMin(MIN_VALUE.value(), value);
            checkMax(MAX_VALUE.value(), value);
        }
        this.value = value.setScale(4, HALF_EVEN);
    }

    private Percentage(String value) {
        checkNotNull("value", value);
        String normalized = value.strip();
        boolean negative = false;
        if (normalized.startsWith("-")) {
            negative = true;
            normalized = normalized.substring(1);
        }
        checkMatches(PATTERN, normalized);
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
        checkMin(MIN_VALUE.value(), decimalValue);
        checkMax(MAX_VALUE.value(), decimalValue);
        this.value = decimalValue.setScale(4, HALF_EVEN);
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
        return new Percentage(value);
    }

    @Override
    public Percentage add(DataPointValue obj) {
        Percentage that = checkInstanceOf(Percentage.class, obj);
        return new Percentage(this.value.add(that.value));
    }

    @Override
    public Percentage sub(DataPointValue obj) {
        Percentage that = checkInstanceOf(Percentage.class, obj);
        return new Percentage(this.value.subtract(that.value));
    }

    @Override
    public Percentage mul(DataPointValue obj) {
        Percentage that = checkInstanceOf(Percentage.class, obj);
        return new Percentage(this.value.multiply(that.value));
    }

    @Override
    public Percentage div(DataPointValue obj) {
        Percentage that = checkInstanceOf(Percentage.class, obj);
        if (that.isZero()) {
            throw new FQLError("[Percentage#div] cannot divide by zero");
        }
        return new Percentage(this.value.divide(that.value, DECIMAL32));
    }

    @Override
    public Percentage neg() {
        return new Percentage(this.value.negate());
    }

    @Override
    public Percentage abs() {
        return new Percentage(this.value.abs());
    }

    @Override
    public DataPointValue zero() {
        return ZERO;
    }

    @Override
    public boolean eq(DataPointValue obj) {
        Percentage that = checkInstanceOf(Percentage.class, obj);
        return compareTo(that) == 0;
    }

    @Override
    public boolean gt(DataPointValue obj) {
        Percentage that = checkInstanceOf(Percentage.class, obj);
        return compareTo(that) > 0;
    }

    @Override
    public boolean lt(DataPointValue obj) {
        Percentage that = checkInstanceOf(Percentage.class, obj);
        return compareTo(that) < 0;
    }

    @Override
    public BigDecimal value() {
        return value;
    }

    public double doubleValue() {
        return value.doubleValue();
    }

    @Override
    public int compareTo(DataPointValue obj) {
        Percentage that = checkInstanceOf(Percentage.class, obj);
        return this.value.compareTo(that.value);
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
        BigDecimal absoluteValue = value.abs();
        String percentageString = absoluteValue
            .movePointRight(2)
            .stripTrailingZeros()
            .toPlainString() + "%";
        return value.signum() < 0 ? "-" + percentageString : percentageString;
    }
}