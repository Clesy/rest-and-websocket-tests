package config;

import utils.ConfigReader;

import java.util.Properties;

public class ConnectConfig {

    private static final String CONFIG_FILE_NAME = "connect.properties";

    private static final String APPLICATION_KEY_PROPERTY_NAME = "app.key";
    private static final String APPLICATION_ID_PROPERTY_NAME = "app.id";
    private static final String APPLICATION_URI_PROPERTY_NAME = "api.server.uri";

    private static final Properties CONNECT_CONFIG;
    public static final String APPLICATION_KEY;
    public static final String APPLICATION_ID;
    public static final String APPLICATION_URI;

    static {
        CONNECT_CONFIG = ConfigReader.readConfig(CONFIG_FILE_NAME);
        APPLICATION_ID = CONNECT_CONFIG.getProperty(APPLICATION_ID_PROPERTY_NAME);
        APPLICATION_KEY = CONNECT_CONFIG.getProperty(APPLICATION_KEY_PROPERTY_NAME);
        APPLICATION_URI = CONNECT_CONFIG.getProperty(APPLICATION_URI_PROPERTY_NAME);
    }

    private ConnectConfig(){

    }
}
