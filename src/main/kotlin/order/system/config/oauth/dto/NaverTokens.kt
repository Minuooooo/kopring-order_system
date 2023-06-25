package order.system.config.oauth.dto

import com.fasterxml.jackson.annotation.JsonProperty

class NaverTokens(
        @JsonProperty("access_token")
        private val accessToken: String,

        @JsonProperty("refresh_token")
        private val refreshToken: String,

        @JsonProperty("token_type")
        private val tokenType: String,

        @JsonProperty("expires_in")
        private val expiresIn: String
)