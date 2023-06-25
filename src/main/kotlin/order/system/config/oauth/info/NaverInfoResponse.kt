package order.system.config.oauth.info

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import order.system.config.oauth.common.OAuthInfoResponse

@JsonIgnoreProperties(ignoreUnknown = true)
class NaverInfoResponse(
        @JsonProperty("response")
        private val response: Response
) : OAuthInfoResponse {

    companion object {
        class Response(
                val email: String,
                val nickname: String
        )
    }

    override fun getEmail(): String = response.email
    override fun getNickname(): String = response.nickname
}