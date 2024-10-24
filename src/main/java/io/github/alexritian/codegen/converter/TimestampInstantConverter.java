package io.github.alexritian.codegen.converter;

import org.jooq.Converter;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * @author Too_young
 */
public class TimestampInstantConverter implements Converter<Timestamp, Instant> {

    @Override
    @Nullable
    public Instant from(@Nullable Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toInstant();
    }

    @Override
    @Nullable
    public Timestamp to(@Nullable Instant instant) {
        if (instant == null) {
            return null;
        }
        return Timestamp.from(instant);
    }

    @Override
    public Class<Timestamp> fromType() {
        return Timestamp.class;
    }

    @Override
    public Class<Instant> toType() {
        return Instant.class;
    }
}
