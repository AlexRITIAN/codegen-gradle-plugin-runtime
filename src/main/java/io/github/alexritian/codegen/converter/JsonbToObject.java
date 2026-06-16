package io.github.alexritian.codegen.converter;

import org.jooq.JSONB;
import org.jooq.impl.AbstractConverter;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

/**
 * @author Too_young
 */
public class JsonbToObject extends AbstractConverter<JSONB, Object> {

    private static final ObjectMapper OBJECT_MAPPER = JsonMapper.builder().findAndAddModules().build();


    public JsonbToObject() {
        super(JSONB.class, Object.class);
    }

    @Override
    public Object from(JSONB databaseObject) {
        if (databaseObject == null) {
            return null;
        }
        String s = databaseObject.data();
        if (s.isEmpty() || "null".equalsIgnoreCase(s)) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(s, toType());
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to convert JSONB to Object: " + s, e);
        }
    }

    @Override
    public JSONB to(Object userObject) {
        if (userObject == null) {
            return null;
        }
        try {
            return JSONB.valueOf(OBJECT_MAPPER.writeValueAsString(userObject));
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to convert Object to JSONB ", e);
        }
    }
}
