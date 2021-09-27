package tests;

import clients.ApiGatewayWebSocketClient;
import clients.GameRestServiceClient;
import config.ConnectConfig;
import config.DatabaseConfig;
import convertion.JacksonJsonConverter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.DatabaseConnector;
import utils.RequiresDbConnection;

import java.lang.reflect.Method;
import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseTest {

    private static DatabaseConnector databaseConnector;
    private static GameRestServiceClient gameRestServiceClient;

    private ThreadLocal<ApiGatewayWebSocketClient> apiGatewayWebSocketClient = new ThreadLocal<>();
    private ThreadLocal<Connection> connection = new ThreadLocal<>();

    @BeforeSuite
    public void setUp() {
        databaseConnector = new DatabaseConnector(DatabaseConfig.DATABASE_URL,
                DatabaseConfig.DATABASE_USER, DatabaseConfig.DATABASE_PASSWORD);

        gameRestServiceClient = new GameRestServiceClient(ConnectConfig.APPLICATION_KEY,
                ConnectConfig.APPLICATION_ID, ConnectConfig.APPLICATION_URI, new JacksonJsonConverter());
    }

    @BeforeMethod
    public void openConnectionIfRequired(Method testMethod) {
        apiGatewayWebSocketClient.set(
                new ApiGatewayWebSocketClient(URI.create(Const.Uri),
                        new JacksonJsonConverter()));

        apiGatewayWebSocketClient.get().connect();

        RequiresDbConnection annotation = testMethod.getAnnotation(RequiresDbConnection.class);
        if (annotation == null) {
            annotation = testMethod.getDeclaringClass().getAnnotation(RequiresDbConnection.class);
        }
        if (annotation == null) return;

        //TODO Вынести коннект apiGatewayWebSocketClient в отдельный Befo.. метод
        //TODO Jackson convert можно создать один экземпляр в BeforeSuite и сохранить в static поле

        connection.set(databaseConnector.openConnection());
    }

    @AfterMethod
    public void closeConnection() {
        if (connection.get() != null) {
            try {
                connection.get().close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                connection = null;
            }
        }
    }

    @AfterMethod
    public void closeWebSocketConnection() {

        apiGatewayWebSocketClient.get().disconnect();
        apiGatewayWebSocketClient = null;
    }

    protected static DatabaseConnector databaseConnector() {
        return databaseConnector;
    }

    protected static GameRestServiceClient gameServiceClient() {
        return gameRestServiceClient;
    }

    protected final ApiGatewayWebSocketClient apiGatewayWebSocketClient() {
        return apiGatewayWebSocketClient.get();
    }

    protected final Connection connection() {
        return connection.get();
    }

}
