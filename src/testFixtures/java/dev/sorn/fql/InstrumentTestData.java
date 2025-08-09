package dev.sorn.fql;

import dev.sorn.fql.api.Instrument;
import java.util.concurrent.ThreadLocalRandom;

public interface InstrumentTestData {
    default Instrument aRandomInstrument() {
        String chars = "CLUcluFOXfoxIRir369";
        StringBuilder s = new StringBuilder(7);
        for (int i = 0; i < 7; i++) {
            s.append(chars.charAt(ThreadLocalRandom.current().nextInt(chars.length())));
        }
        return s::toString;
    }
}