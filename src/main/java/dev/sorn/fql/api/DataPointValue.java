package dev.sorn.fql.api;

import java.math.BigDecimal;

public interface DataPointValue extends Comparable<DataPointValue>, ValueObject<BigDecimal> {
    DataPointValue add(DataPointValue obj);
    DataPointValue sub(DataPointValue obj);
    DataPointValue mul(DataPointValue obj);
    DataPointValue div(DataPointValue obj);
    DataPointValue neg();
    DataPointValue abs();
    DataPointValue zero();

    boolean eq(DataPointValue obj);

    boolean gt(DataPointValue obj);

    boolean lt(DataPointValue obj);

    default boolean gte(DataPointValue obj) {
        return eq(obj) || gt(obj);
    }

    default boolean lte(DataPointValue obj) {
        return eq(obj) || lt(obj);
    }

    default boolean isPositive() {
        return gt(zero());
    }

    default boolean isNegative() {
        return lt(zero());
    }

    default boolean isZero() {
        return eq(zero());
    }
}