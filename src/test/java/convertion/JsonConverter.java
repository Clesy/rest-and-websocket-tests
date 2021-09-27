package convertion;


public interface JsonConverter {
    <T> String toJson(T object);
    <T> T fromJson(String json, Class<T> type);
    <T> T fromJson(String json, Class<?> parameterizedType, Class<?>... parameterTypes);
}
