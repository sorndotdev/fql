package dev.sorn.fql.api;

import java.math.BigDecimal;
import java.util.Objects;
import static dev.sorn.fql.api.Checks.checkEquals;
import static dev.sorn.fql.api.Checks.checkInstanceOf;
import static dev.sorn.fql.api.Checks.checkNotNull;
import static java.math.BigDecimal.ZERO;
import static java.math.MathContext.DECIMAL32;
import static java.math.RoundingMode.HALF_EVEN;
import static java.util.Objects.compare;
import static java.util.Objects.hash;

public class Amount implements DataPointValue {
    protected final BigDecimal value;
    protected final Unit unit;

    protected Amount(BigDecimal value, Unit unit) {
        checkNotNull("value", value);
        checkNotNull("unit", unit);
        this.value = value.setScale(unit.minorUnit(), HALF_EVEN);
        this.unit = unit;
    }

    public static Amount amount(String value, Unit unit) {
        checkNotNull("value", value);
        return new Amount(new BigDecimal(value), unit);
    }

    public static Amount amount(BigDecimal value, Unit unit) {
        return new Amount(value, unit);
    }

    @Override
    public Amount add(DataPointValue obj) {
        Amount that = checkAmount(obj);
        return create(value.add(that.value), unit);
    }

    @Override
    public Amount sub(DataPointValue obj) {
        Amount that = checkAmount(obj);
        return create(value.subtract(that.value), unit);
    }

    @Override
    public Amount mul(DataPointValue obj) {
        Amount that = checkAmount(obj);
        return create(value.multiply(that.value), unit);
    }

    @Override
    public Amount div(DataPointValue obj) {
        Amount that = checkAmount(obj);
        return create(value.divide(that.value, DECIMAL32), unit);
    }

    @Override
    public Amount neg() {
        return create(value.negate(), unit);
    }

    @Override
    public Amount abs() {
        return create(value.abs(), unit);
    }

    public Amount zero() {
        return create(ZERO, unit);
    }

    @Override
    public final boolean eq(DataPointValue obj) {
        Amount that = checkAmount(obj);
        return compareTo(that) == 0;
    }

    @Override
    public final boolean gt(DataPointValue obj) {
        Amount that = checkAmount(obj);
        return compareTo(that) > 0;
    }

    @Override
    public final boolean lt(DataPointValue obj) {
        Amount that = checkAmount(obj);
        return compareTo(that) < 0;
    }

    @Override
    public final BigDecimal value() {
        return value;
    }

    public final Unit unit() {
        return unit;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Amount that)) return false;
        if (!Objects.equals(value, that.value)) return false;
        if (!Objects.equals(unit, that.unit)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return hash(value, unit);
    }

    @Override
    public String toString() {
        String formattedValue = value.abs()
            .setScale(unit.minorUnit(), HALF_EVEN)
            .stripTrailingZeros()
            .toPlainString();
        String signedValue = value.signum() < 0 ? "-" + formattedValue : formattedValue;
        return signedValue + " " + unit.symbol();
    }

    @Override
    public final int compareTo(DataPointValue obj) {
        Amount that = checkAmount(obj);
        return compare(this.value(), that.value(), BigDecimal::compareTo);
    }

    protected final Amount checkAmount(DataPointValue obj) {
        Amount that = checkInstanceOf(Amount.class, obj);
        checkEquals(this.unit(), that.unit());
        return that;
    }

    protected Amount create(BigDecimal value, Unit unit) {
        return new Amount(value, unit);
    }
}