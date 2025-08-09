package dev.sorn.fql;

import dev.sorn.fql.api.Source;
import java.util.concurrent.ThreadLocalRandom;

public interface SourceTestData {
    default Source aRandomSource() {
        String[] sources = {"BLOOMBERG", "REFINITIV"};
        String chosen = sources[ThreadLocalRandom.current().nextInt(sources.length)];
        return () -> chosen;
    }
}