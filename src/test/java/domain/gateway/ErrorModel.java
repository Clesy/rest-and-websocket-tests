package domain.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import domain.ResponseModel;

public record ErrorModel(Type type, @JsonProperty("msg") String message) {

    public enum Type {
        @JsonProperty("internalServerError") INTERNAL_SERVER_ERROR
    }
}
