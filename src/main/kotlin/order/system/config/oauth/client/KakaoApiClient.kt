package order.system.config.oauth.client

import order.system.config.oauth.common.OAuthApiClient
import order.system.config.oauth.common.OAuthInfoResponse
import order.system.config.oauth.common.OAuthLoginParams
import order.system.config.oauth.common.OAuthProvider
import order.system.config.oauth.dto.KakaoTokens
import order.system.config.oauth.info.KakaoInfoResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType.*
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate

class KakaoApiClient(
        private val restTemplate: RestTemplate,

        @Value("\${oauth.kakao.url.auth}")
        private val authUrl: String,

        @Value("\${oauth.kakao.url.api}")
        private val apiUrl: String,

        @Value("\${oauth.kakao.client-id}")
        private val clientId: String
) : OAuthApiClient {

    companion object {
        private const val GRANT_TYPE = "authorization_code"
    }

    override fun oAuthProvider(): OAuthProvider = OAuthProvider.KAKAO

    override fun requestAccessToken(params: OAuthLoginParams): String? {

        val url = "$authUrl/oauth/token";  // Token provider url

        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = APPLICATION_FORM_URLENCODED  // Query parameter 형식으로 변환

        val body = params.makeBody()
        body.add("grant_type", GRANT_TYPE)
        body.add("client_id", clientId)

        val request = HttpEntity(body, httpHeaders)
        val response: KakaoTokens? = restTemplate.postForObject(url, request, KakaoTokens::class.java)

        return response?.accessToken
    }

    override fun requestOAuthInfo(accessToken: String?): OAuthInfoResponse? {

        val url = "$apiUrl/v2/user/me"

        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = APPLICATION_FORM_URLENCODED
        httpHeaders.set("Authorization", "Bearer $accessToken")

        val body: MultiValueMap<String, String> = LinkedMultiValueMap()
        body.add("property_keys", "[\"kakao_account_email\", \"kakao_account.profile\"]")

        val request = HttpEntity(body, httpHeaders)

        return restTemplate.postForObject(url, request, KakaoInfoResponse::class.java)
    }
}