package domain.spin;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Spin(@JsonProperty("LastSpinDate") Long lastSpinDate,
                   @JsonProperty("AdSpinsAmount") Integer adSpinsAmount) {
}
