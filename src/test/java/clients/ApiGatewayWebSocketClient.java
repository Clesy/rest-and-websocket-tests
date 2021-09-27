package clients;

import convertion.JsonConverter;
import domain.gateway.ApiGatewayMessage;
import domain.gateway.ApiGatewayRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public final class ApiGatewayWebSocketClient {
    private static final Logger logger = LogManager.getLogger(ApiGatewayWebSocketClient.class);
    private static final int RESPONSE_QUEUE_CAPACITY = 10;

    private final URI uri;
    private final JsonConverter jsonConverter;

    private BlockingQueue<String> responseTextQueue;
    private HttpClient httpClient;
    private WebSocket webSocket;

    public ApiGatewayWebSocketClient(URI uri, JsonConverter jsonConverter) {
        this.uri = uri;
        this.jsonConverter = jsonConverter;
    }

    public void connect() {
        if (isConnected()) {
            throw new IllegalStateException("WebSocket client is already connected");
        }

        if (httpClient == null) {
            httpClient = HttpClient.newHttpClient();
        }
        responseTextQueue = new ArrayBlockingQueue<>(RESPONSE_QUEUE_CAPACITY);
        webSocket = httpClient.newWebSocketBuilder().buildAsync(uri, new WebSocketListener(responseTextQueue)).join();
    }

    public void disconnect() {
        if (!isConnected()) {
            throw new IllegalStateException("WebSocket client is already disconnect");
        }
        webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "Normal closure").join();
        webSocket = null;
        responseTextQueue = null;
    }

    public String sendRequest(String json) {
        logger.info("------------------------------------------------------------------");
        logger.info("Sending WebSocket request: " + json);
        if (!isConnected()) {
            throw new IllegalStateException("WebSocket client is already disconnect");
        }
        webSocket.sendText(json, true);
        try {
            String responseText = responseTextQueue.poll(5, TimeUnit.SECONDS);

            if (responseText == null) {
                throw new RuntimeException("Response message was not received");
            }
            logger.info("Full WebSocket response message received");
            logger.info("------------------------------------------------------------------");

            return responseText;
        } catch (
                InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public <T, E> ApiGatewayMessage<T, E> sendRequest(
            ApiGatewayRequest<T> requestModel, Class<E> responseValueType) {

        String responseJson =
                sendRequest(jsonConverter.toJson(requestModel == null ? "" : requestModel));

        return jsonConverter.fromJson(
                responseJson,
                ApiGatewayMessage.class,
                requestModel.getParametersType(),
                responseValueType);
    }

    // readme как запустить  env
    // как ранить тесты

    public boolean isConnected() {
        return webSocket != null && !webSocket.isOutputClosed() && !webSocket.isInputClosed();
    }

    private static class WebSocketListener implements WebSocket.Listener {
        private static final Logger logger = LogManager.getLogger(WebSocketListener.class);

        final BlockingQueue<String> responseTextQueue;
        String responseTexts = "";

        WebSocketListener(BlockingQueue<String> responseTextQueue) {
            this.responseTextQueue = responseTextQueue;
        }

        @Override
        public void onOpen(WebSocket webSocket) {
            logger.info("WebSocket connection opened ");
            WebSocket.Listener.super.onOpen(webSocket);
        }

        @Override
        public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
            logger.info("WebSocket message received ({}): {}", last ? "last" : "not last", data);

            responseTexts += data.toString();
            if (last) {
                try {
                    responseTextQueue.offer(responseTexts, 5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                responseTexts = "";
            }
            return WebSocket.Listener.super.onText(webSocket, data, last);
        }

        @Override
        public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
            logger.info("WebSocked connection closed ");
            return WebSocket.Listener.super.onClose(webSocket, statusCode, reason);
        }

        @Override
        public void onError(WebSocket webSocket, Throwable error) {
            logger.error("WebSocket error appeared: " + error);
            WebSocket.Listener.super.onError(webSocket, error);
        }
    }
}
