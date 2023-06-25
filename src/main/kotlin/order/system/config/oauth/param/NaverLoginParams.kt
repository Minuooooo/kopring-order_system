package order.system.config.oauth.param

import order.system.config.oauth.common.OAuthLoginParams
import order.system.config.oauth.common.OAuthProvider
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

class NaverLoginParams(
        private val authorizationCode: String,
        private val state: String
) : OAuthLoginParams {

    override fun oAuthProvider(): OAuthProvider = OAuthProvider.NAVER

    override fun makeBody(): MultiValueMap<String, String> {
        val body: MultiValueMap<String, String> = LinkedMultiValueMap()
        body.add("code", authorizationCode)
        body.add("state", state)
        return body
    }
}