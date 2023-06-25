package order.system.config.oauth.param

import order.system.config.oauth.common.OAuthLoginParams
import order.system.config.oauth.common.OAuthProvider
import org.springframework.util.MultiValueMap

class KakaoLoginParams : OAuthLoginParams {

    private val authorizationCode: String? = null
    private val state: String? = null

    override fun oAuthProvider(): OAuthProvider {

    }

    override fun makeBody(): MultiValueMap<String, String> {
        TODO("Not yet implemented")
    }
}