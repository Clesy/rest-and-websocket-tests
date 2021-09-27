package config;

import utils.ConfigReader;

import java.util.Properties;

public class DatabaseConfig {
    private static final String CONFIG_FILE_NAME = "database.properties";

    private final static String DATABASE_URL_PROPERTY_NAME = "database.url";
    private final static String DATABASE_USER_PROPERTY_NAME = "database.username";
    private final static String DATABASE_PASSWORD_PROPERTY_NAME = "database.password";

    private static final Properties DATABASE_CONFIG;
    public static final String DATABASE_URL;
    public static final String DATABASE_USER;
    public static final String DATABASE_PASSWORD;


    static {
        DATABASE_CONFIG = ConfigReader.readConfig(CONFIG_FILE_NAME);
        DATABASE_URL = DATABASE_CONFIG.getProperty(DATABASE_URL_PROPERTY_NAME);
        DATABASE_USER = DATABASE_CONFIG.getProperty(DATABASE_USER_PROPERTY_NAME);
        DATABASE_PASSWORD = DATABASE_CONFIG.getProperty(DATABASE_PASSWORD_PROPERTY_NAME);
    }

    private DatabaseConfig() {
    }
}
