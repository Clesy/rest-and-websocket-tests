package domain.spin;

import com.fasterxml.jackson.annotation.JsonProperty;
import domain.ResponseModel;

public record SpinData(@JsonProperty("LastSpinDate") Long spinDate,
                       @JsonProperty("AdSpinsAmount") Integer spinAmount,
                       @JsonProperty("Status") Integer status) implements ResponseModel {
}
