package convertion;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public final class JacksonJsonConverter implements JsonConverter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> String toJson(T object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T fromJson(String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T fromJson(String json, Class<?> parameterizedType, Class<?>... parameterTypes) {
        try {
            return objectMapper.readValue(
                    json,
                    objectMapper.getTypeFactory().constructParametricType(
                            parameterizedType,
                            parameterTypes));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
