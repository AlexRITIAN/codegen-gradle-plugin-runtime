package io.github.alexritian.codegen.converter;

import org.jetbrains.annotations.Nullable;
import org.jooq.impl.AbstractConverter;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * @author Too_young
 */
public class OffsetDateTimeInstantConverter extends AbstractConverter<OffsetDateTime, Instant> {

    public OffsetDateTimeInstantConverter() {
        super(OffsetDateTime.class, Instant.class);
    }

    @Override @Nullable
    public Instant from(@Nullable OffsetDateTime odt) {
        return odt == null ? null : odt.toInstant();
    }

    @Override @Nullable
    public OffsetDateTime to(@Nullable Instant instant) {
        return instant == null ? null : instant.atOffset(ZoneOffset.UTC);
    }

}
