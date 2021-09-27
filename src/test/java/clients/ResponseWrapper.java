package clients;

import convertion.JsonConverter;

import java.net.http.HttpResponse;

public final class ResponseWrapper {

    private final HttpResponse<String> response;
    private final JsonConverter jsonConverter;

    public ResponseWrapper(HttpResponse<String> response, JsonConverter jsonConverter) {
        this.response = response;
        this.jsonConverter = jsonConverter;
    }

    public int status() {
        return response.statusCode();
    }

    public String bodyAsString() {
        return response.body();
    }

    public <T> T toModel(Class<T> modelType) {
        return jsonConverter.fromJson(bodyAsString(), modelType);
    }

    String uri() {
        return response.uri().toString();
    }

    @Override
    public String toString() {
        return response.toString();
    }
}
