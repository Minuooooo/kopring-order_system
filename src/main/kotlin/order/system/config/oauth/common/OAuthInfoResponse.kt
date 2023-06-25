package order.system.config.oauth.common

interface OAuthInfoResponse {
    fun getEmail(): String
    fun getNickname(): String
}