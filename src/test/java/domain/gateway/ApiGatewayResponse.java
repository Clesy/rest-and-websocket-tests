package domain.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import domain.ResponseModel;

public record ApiGatewayResponse<T>(Type type, T value) {

    public enum Type {
        @JsonProperty("Right") RIGHT,
        @JsonProperty("Left") LEFT
    }
}
