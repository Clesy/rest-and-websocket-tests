package clients;

import domain.card.UserCardData;
import domain.deviceauthorization.DeviceAuthorizationData;
import domain.achievements.AchievementsList;
import domain.appconfig.AppConfigList;
import domain.registration.RegistrationData;
import domain.task.CurrentTaskList;
import domain.time.TimeInfo;
import domain.token.TokenValidationData;
import domain.version.MergeCatVersionData;

public enum Endpoint {
    TASK_LIST("/tasklist", CurrentTaskList.class),
    APP_CONFIG("/appsettings", AppConfigList.class),
    DEVICE_AUTHORIZATION("/profile", DeviceAuthorizationData.class),
    REGISTER_AT_SITE("/registeratsite", RegistrationData.class),
    TOKEN("/validation", TokenValidationData.class),
    CAT_VERSION("/mergeclientversion", MergeCatVersionData.class),
    GET_CARD("/getcards", UserCardData.class),
    TIME("/time", TimeInfo.class),
    ACHIEVEMENTS("/achievements", AchievementsList.class);

    private final String endpointPath;
    private final Class<?> defaultResponseModelType;

    Endpoint(String endpointPath, Class<?> defaultResponseModelType) {
        this.endpointPath = endpointPath;
        this.defaultResponseModelType = defaultResponseModelType;
    }

    public String endpointPath() {
        return endpointPath;
    }

    public Class<?> defaultResponseModelType() {
        return defaultResponseModelType;
    }
}
