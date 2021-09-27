package domain.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ApiGatewayRequestMethod {
    @JsonProperty("lobby_achievementComplete") COMPLETE_LOBBY_ACHIEVEMENT,
    @JsonProperty("lobby_getAllAchievements") GET_ALL_ACHIEVEMENTS,
    @JsonProperty("lobby_getCurrentTasks") GET_CURRENT_TASKS,
    @JsonProperty("lobby_getServerTime") GET_SERVER_TIME,
    @JsonProperty("lobby_getClientVersion") GET_CLIENT_VERSION,
    @JsonProperty("lobby_keepAlive") GET_KEEP_ALIVE,
    @JsonProperty("lobby_getUsersAchievements") GET_USERS_ACHIEVEMENTS,
    @JsonProperty("lobby_getUsersCards") GET_USERS_CARDS,
    @JsonProperty("lobby_getSpinRewards") GET_USERS_SPIN,
}
