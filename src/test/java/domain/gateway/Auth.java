package domain.gateway;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record Auth(String token, Type type, String deviceId) {

    public Auth(String token, Type type) {
        this(token, type, null);
    }

    public enum Type {
        @JsonProperty("webSite") WEB_SITE,
        @JsonProperty("mobileApp") MOBILE_APP
    }

}
