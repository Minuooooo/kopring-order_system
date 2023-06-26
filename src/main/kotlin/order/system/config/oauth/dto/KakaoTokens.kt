package order.system.config.oauth.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoTokens(
        @JsonProperty("access_token")
        val accessToken: String,

        @JsonProperty("refresh_token")
        private val refreshToken: String,

        @JsonProperty("token_type")
        private val tokenType: String,

        @JsonProperty("expires_in")
        private val expiresIn: String,

        @JsonProperty("refresh_token_expires_in")
        private val refreshTokenExpiresIn: String,

        @JsonProperty("scope")
        private val scope: String
)