package domain.gateway;


import com.fasterxml.jackson.annotation.JsonIgnore;

public record ApiGatewayRequest<T>(
        String agent,
        Auth auth,
        String id,
        String lang,
        ApiGatewayRequestMethod method,
        T params) {

    @JsonIgnore
    public Class<?> getParametersType() {
        return params != null ? params.getClass() : Object.class;
    }
}