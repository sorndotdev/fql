package dev.sorn.fql.api;

import java.math.BigDecimal;

public interface DataPointValue extends Comparable<DataPointValue>, ValueObject<BigDecimal> {
    DataPointValue add(DataPointValue that);
    DataPointValue sub(DataPointValue that);
    DataPointValue mul(DataPointValue that);
    DataPointValue div(DataPointValue that);
    DataPointValue neg();
}