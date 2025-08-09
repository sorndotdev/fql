package dev.sorn.fql.api;

import dev.sorn.fql.InstrumentTestData;
import org.junit.jupiter.api.Test;
import static dev.sorn.fql.api.Instrument.instrument;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InstrumentTest implements InstrumentTestData {
    @Test
    void instrument_value() {
        Instrument instrument = instrument("AAPL");
        assertEquals("AAPL", instrument.value());
    }

    @Test
    void instrument_random_value() {
        Instrument instrument = aRandomInstrument();
        assertNotNull(instrument.value());
    }

    @Test
    void instrument_is_interface() {
        assertTrue(Instrument.class.isInterface(), "Instrument should be an interface");
    }

    @Test
    void instrument_functional_interface_annotation() {
        boolean isAnnotated = Instrument.class.isAnnotationPresent(FunctionalInterface.class);
        assertTrue(isAnnotated, "Instrument should be annotated with @FunctionalInterface");
    }

    @Test
    void instrument_implements_value_object() {
        Instrument instrument = instrument("AAPL");
        assertTrue(instrument instanceof ValueObject);
    }
}