package tests;

import convertion.DbToModelConverter;
import dao.SpinDao;
import domain.gateway.ApiGatewayRequest;
import domain.gateway.ApiGatewayResponse;
import domain.gateway.Auth;
import domain.spin.SpinData;
import domain.spin.SpinParams;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.RequiresDbConnection;

import static domain.gateway.ApiGatewayRequestMethod.GET_USERS_SPIN;
import static domain.gateway.Auth.Type.WEB_SITE;
import static utils.DataGenerator.generateUuid;
import static utils.UsersId.SPIN_USER;

public class SpinRewardTest extends BaseTest {

    @RequiresDbConnection
    @Test
    public void test() {
        SpinData expectedCurrentTaskList = DbToModelConverter
                .toSpinData(new SpinDao(connection()).getSpinReward(SPIN_USER), 0);
        ApiGatewayRequest<SpinParams> apiGatewayRequest = new ApiGatewayRequest<>(
                "cats",
                new Auth("1", WEB_SITE),
                generateUuid(),
                "en",
                GET_USERS_SPIN,
                new SpinParams(SPIN_USER));

        ApiGatewayResponse<SpinData> response = apiGatewayWebSocketClient()
                .sendRequest(apiGatewayRequest, SpinData.class)
                .result().response();

        ApiGatewayResponse<SpinData> expectedResponse = new ApiGatewayResponse<>(
                ApiGatewayResponse.Type.RIGHT, expectedCurrentTaskList);

        Assert.assertEquals(response, expectedResponse, "Incorrect current task list");
    }
}
