package clients;

import convertion.JsonConverter;
import domain.ResponseModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class GameRestServiceClient {
    private static final Logger logger = LogManager.getLogger();

    private final String key;
    private final String appId;
    private final String baseUrl;
    private final JsonConverter jsonConverter;
    private final HttpClient httpClient;

    public GameRestServiceClient(String key, String appId, String baseUrl, JsonConverter jsonConverter) {
        this.key = key;
        this.appId = appId;
        this.baseUrl = baseUrl;
        this.jsonConverter = jsonConverter;

        httpClient = HttpClient.newHttpClient();
    }

    public ResponseWrapper sendGetRequest(Endpoint endpoint, String json) {
        var request = HttpRequest.newBuilder()
                .uri(buildUri(endpoint.endpointPath(), json))
                .GET()
                .build();
        return sendRequest(request);

    }

    public ResponseWrapper sendGetRequest(Endpoint endpoint, Object jsonObject) {
        return sendGetRequest(endpoint, jsonConverter.toJson(jsonObject == null ? "" : jsonObject));
    }

    public ResponseWrapper sendGetRequest(Endpoint endpoint) {
        return sendGetRequest(endpoint, "");
    }

    public <T extends ResponseModel> T createResponseModel(Endpoint endpoint, String json, Class<T> responseModelType) {
        ResponseWrapper response = sendGetRequest(endpoint, json);
        if (response.status() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Invalid status code");
        }
        return response.toModel(responseModelType);
    }

    public <T extends ResponseModel> T createResponseModel(Endpoint endpoint, Object jsonObject, Class<T> responseModelType) {
        return createResponseModel(endpoint, jsonConverter.toJson(jsonObject == null ? "" : jsonObject), responseModelType);
    }

    public <T extends ResponseModel> T createResponseModel(Endpoint endpoint, Class<T> responseModelType) {
        return createResponseModel(endpoint, "", responseModelType);
    }

    @SuppressWarnings("unchecked")
    public <T extends ResponseModel> T createResponseModel(Endpoint endpoint, String json) { // sendGetRequestWithConvertedResult
        return createResponseModel(endpoint, json, (Class<T>) endpoint.defaultResponseModelType()); //sendGetRequestWithResult
                                                                                                    // sendGetRequest().toModel()
    }

    @SuppressWarnings("unchecked")
    public <T extends ResponseModel> T createResponseModel(Endpoint endpoint, Object jsonObject) {
        return createResponseModel(endpoint, jsonObject, (Class<T>) endpoint.defaultResponseModelType());
    }

    public <T extends ResponseModel> T createResponseModel(Endpoint endpoint) {
        return createResponseModel(endpoint, "");
    }

    private ResponseWrapper sendRequest(HttpRequest request) {
        logger.info("Sending request {} ", request);
        try {
            ResponseWrapper response = new ResponseWrapper(
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString()),
                    jsonConverter);
            logger.info("{} {}\n> Body: {}", response.status(), response.uri(), response.bodyAsString());

            return response;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String encodeUrlPath(String path) {
        try {
            return URLEncoder.encode(path, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> URI buildUri(String endpointPath, String json) {
        logger.info("Building URI: path={}, key={}, appId={}, json={}",
                endpointPath, key, appId, json);
        return URI.create(baseUrl + endpointPath + "?key=" + key + "&appId="
                + appId + "&json=" + encodeUrlPath(json));
    }
}