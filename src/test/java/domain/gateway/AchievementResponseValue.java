package domain.gateway;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import domain.ResponseModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AchievementResponseValue(
        @JsonProperty("Status") Integer status,
        @JsonProperty("Key") String key) implements ResponseModel {}
