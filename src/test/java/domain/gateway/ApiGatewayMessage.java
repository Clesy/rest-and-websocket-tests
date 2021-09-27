package domain.gateway;

import domain.ResponseModel;

public record ApiGatewayMessage<T, E>(
        ApiGatewayResult<T, E> result) {
}
