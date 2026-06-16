package io.github.alexritian.codegen.converter;

import org.jooq.JSONB;
import org.jooq.impl.AbstractConverter;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

import java.util.Map;
import java.util.Objects;

/**
 * @author Too_young
 */
public class JsonbToMapConverter extends AbstractConverter<JSONB, Map<String, String>> {

    @SuppressWarnings("rawtypes")
    private static final Class<Map> MAP_CLASS = Map.class;

    private static final TypeReference<Map<String, String>> MAP_TYPE = new TypeReference<>() {};

    private static volatile ObjectMapper OBJECT_MAPPER = JsonMapper.builder().findAndAddModules().build();

    public JsonbToMapConverter() {
        super(JSONB.class, castMapClass());
    }

    public static void setObjectMapper(ObjectMapper mapper) {
        OBJECT_MAPPER = Objects.requireNonNull(mapper, "mapper");
    }

    @Override
    public Map<String, String> from(JSONB databaseObject) {
        if (databaseObject == null) {
            return null;
        }

        String json = databaseObject.data();
        String s = json.trim();
        if (s.isEmpty() || "null".equals(s)) {
            return null;
        }

        try {
            return OBJECT_MAPPER.readValue(s, MAP_TYPE);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to convert JSONB to Map<String,String>: " + s, e);
        }
    }

    @Override
    public JSONB to(Map<String, String> userObject) {
        if (userObject == null) {
            return null;
        }

        try {
            return JSONB.valueOf(OBJECT_MAPPER.writeValueAsString(userObject));
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to convert Map<String,Object> to JSONB", e);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static Class<Map<String, String>> castMapClass() {
        return (Class) MAP_CLASS;
    }
}
