package dev.sorn.fql.api;

import dev.sorn.fql.FiscalPeriodRangeTestData;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static dev.sorn.fql.api.FiscalPeriodRange.fiscalPeriodRange;
import static dev.sorn.fql.api.Optionality.optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FiscalPeriodRangeTest implements FiscalPeriodRangeTestData {
    @Test
    void fiscalPeriodRange_valid_string_both_present() {
        // given
        String s = "1999-2000";

        // when
        FiscalPeriodRange range = fiscalPeriodRange(s);

        // then
        assertTrue(range.startInclusive().isPresent());
        assertTrue(range.endInclusive().isPresent());
        assertEquals("1999", range.startInclusive().get().toString());
        assertEquals("2000", range.endInclusive().get().toString());
    }

    @Test
    void fiscalPeriodRange_valid_string_with_quarters_both_present() {
        // given
        String s = "1999Q4-2000Q1";

        // when
        FiscalPeriodRange range = fiscalPeriodRange(s);

        // then
        assertTrue(range.startInclusive().isPresent());
        assertTrue(range.endInclusive().isPresent());
        assertEquals("1999Q4", range.startInclusive().get().toString());
        assertEquals("2000Q1", range.endInclusive().get().toString());
    }

    @Test
    void fiscalPeriodRange_valid_string_with_quarters_left_present() {
        // given
        String s = "1999Q4-";

        // when
        FiscalPeriodRange range = fiscalPeriodRange(s);

        // then
        assertTrue(range.startInclusive().isPresent());
        assertTrue(range.endInclusive().isEmpty());
        assertEquals("1999Q4", range.startInclusive().get().toString());
        assertEquals("1999Q4-", range.toString());
    }

    @Test
    void fiscalPeriodRange_valid_string_with_quarters_right_present() {
        // given
        String s = "-1999Q4";

        // when
        FiscalPeriodRange range = fiscalPeriodRange(s);

        // then
        assertTrue(range.startInclusive().isEmpty());
        assertTrue(range.endInclusive().isPresent());
        assertEquals("1999Q4", range.endInclusive().get().toString());
        assertEquals("-1999Q4", range.toString());
    }

    @Test
    void fiscalPeriodRange_valid_string_empty_range() {
        // given
        String s = "-";

        // when
        FiscalPeriodRange range = fiscalPeriodRange(s);

        // then
        assertTrue(range.startInclusive().isEmpty());
        assertTrue(range.endInclusive().isEmpty());
        assertEquals("-", range.toString());
    }

    @Test
    void fiscalPeriodRange_valid_range() {
        // given
        Optional<FiscalPeriod> start = optional(aFiscalPeriod("1999"));
        Optional<FiscalPeriod> end = optional(aFiscalPeriod("2000"));

        // when
        FiscalPeriodRange range = fiscalPeriodRange(start, end);

        // then
        assertEquals(start, range.startInclusive());
        assertEquals(end, range.endInclusive());
    }

    @Test
    void fiscalPeriodRange_start_after_end_throws() {
        // given
        Optional<FiscalPeriod> start = optional(aFiscalPeriod("2000"));
        Optional<FiscalPeriod> end = optional(aFiscalPeriod("1999"));

        // when
        FQLError e = assertThrows(FQLError.class, () -> fiscalPeriodRange(start, end));

        // then
        assertEquals("'fiscalPeriodRange.startInclusive' is after 'fiscalPeriodRange.endInclusive': 2000 < 1999", e.getMessage());
    }

    @Test
    void equals_same_true() {
        // given
        FiscalPeriodRange range = aFiscalPeriodRange("1999", "2000");

        // when
        boolean equal = range.equals(range);

        // then
        assertTrue(equal);
    }

    @Test
    void equals_identical_true() {
        // given
        FiscalPeriodRange range1 = aFiscalPeriodRange("1999", "2000");
        FiscalPeriodRange range2 = aFiscalPeriodRange("1999", "2000");

        // when
        boolean equal = range1.equals(range2);

        // then
        assertTrue(equal);
    }

    @Test
    void equals_different_start_false() {
        // given
        FiscalPeriodRange range1 = aFiscalPeriodRange("1999", "2000");
        FiscalPeriodRange range2 = aFiscalPeriodRange("1998", "2000");

        // when
        boolean equal = range1.equals(range2);

        // then
        assertFalse(equal);
    }

    @Test
    void equals_different_end_false() {
        // given
        FiscalPeriodRange range1 = aFiscalPeriodRange("1999", "2000");
        FiscalPeriodRange range2 = aFiscalPeriodRange("1999", "2001");

        // when
        boolean equal = range1.equals(range2);

        // then
        assertFalse(equal);
    }

    @Test
    void equals_different_class_false() {
        // given
        FiscalPeriodRange range = aFiscalPeriodRange("1999", "2000");
        Object other = new Object();

        // when
        boolean equal = range.equals(other);

        // then
        assertFalse(equal);
    }

    @Test
    void equals_null_false() {
        // given
        FiscalPeriodRange range = aFiscalPeriodRange("1999", "2000");

        // when
        boolean equal = range.equals(null);

        // then
        assertFalse(equal);
    }

    @Test
    void equals_empty_vs_present_false() {
        // given
        FiscalPeriodRange range1 = aFiscalPeriodRange(null, null);
        FiscalPeriodRange range2 = aFiscalPeriodRange("1999", null);

        // when
        boolean equal = range1.equals(range2);

        // then
        assertFalse(equal);
    }

    @Test
    void hashCode_same_for_equal_objects() {
        // given
        FiscalPeriodRange range1 = aFiscalPeriodRange("1999", "2000");
        FiscalPeriodRange range2 = aFiscalPeriodRange("1999", "2000");

        // when
        int hash1 = range1.hashCode();
        int hash2 = range2.hashCode();

        // then
        assertEquals(hash1, hash2);
    }

    @Test
    void hashCode_different_for_different_objects() {
        // given
        FiscalPeriodRange range1 = aFiscalPeriodRange("1999", "2000");
        FiscalPeriodRange range2 = aFiscalPeriodRange("1999", "2001");

        // when
        int hash1 = range1.hashCode();
        int hash2 = range2.hashCode();

        // then
        assertNotEquals(hash1, hash2);
    }

    @Test
    void toString_year_range_representation() {
        // given
        FiscalPeriodRange range = aFiscalPeriodRange("1999", "2000");

        // when
        String str = range.toString();

        // then
        assertEquals("1999-2000", str);
    }

    @Test
    void toString_year_quarter_range_representation() {
        // given
        FiscalPeriodRange range = aFiscalPeriodRange("1999Q1", "2000Q4");

        // when
        String str = range.toString();

        // then
        assertEquals("1999Q1-2000Q4", str);
    }

    @Test
    void toString_year_quarter_range_left_present_representation() {
        // given
        FiscalPeriodRange range = aFiscalPeriodRange("1999Q1", null);

        // when
        String str = range.toString();

        // then
        assertEquals("1999Q1-", str);
    }

    @Test
    void toString_year_quarter_range_right_present_representation() {
        // given
        FiscalPeriodRange range = aFiscalPeriodRange(null, "2000Q4");

        // when
        String str = range.toString();

        // then
        assertEquals("-2000Q4", str);
    }

    @Test
    void toString_year_quarter_range_none_present_representation() {
        // given
        FiscalPeriodRange range = aFiscalPeriodRange(null, null);

        // when
        String str = range.toString();

        // then
        assertEquals("-", str);
    }
}