package order.system.config.oauth.common

import org.springframework.util.MultiValueMap

interface OAuthLoginParams {
    fun oAuthProvider(): OAuthProvider
    fun makeBody(): MultiValueMap<String, String>
}