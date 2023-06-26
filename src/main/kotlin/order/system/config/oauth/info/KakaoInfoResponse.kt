package order.system.config.oauth.info

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import order.system.config.oauth.common.OAuthInfoResponse

@JsonIgnoreProperties(ignoreUnknown = true)
class KakaoInfoResponse(
        @JsonProperty("kakao_account")
        private val kakaoAccount: KakaoAccount
) : OAuthInfoResponse {
    companion object {

        @JsonIgnoreProperties(ignoreUnknown = true)
        data class KakaoAccount(
                val profile: KakaoProfile,
                val email: String
        )

        @JsonIgnoreProperties(ignoreUnknown = true)
        data class KakaoProfile(val nickname: String)
    }

    override fun getEmail(): String = kakaoAccount.email
    override fun getNickname(): String = kakaoAccount.profile.nickname
}