package domain.gateway;

import domain.ResponseModel;

public record ApiGatewayResult<T, E>(
        ApiGatewayRequest<T> request,
        ApiGatewayResponse<E> response) {
}
