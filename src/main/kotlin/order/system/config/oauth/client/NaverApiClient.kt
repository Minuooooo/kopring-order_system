package order.system.config.oauth.client

import order.system.config.oauth.common.OAuthApiClient
import order.system.config.oauth.common.OAuthInfoResponse
import order.system.config.oauth.common.OAuthLoginParams
import order.system.config.oauth.common.OAuthProvider
import order.system.config.oauth.dto.NaverTokens
import order.system.config.oauth.info.NaverInfoResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType.*
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate

class NaverApiClient(
        private val restTemplate: RestTemplate,

        @Value("\${oauth.naver.url.auth}")
        private val authUrl: String,

        @Value("\${oauth.naver.url.api}")
        private val apiUrl: String,

        @Value("\${oauth.naver.client-id}")
        private val clientId: String,

        @Value("\${oauth.naver.secret}")
        private val clientSecret: String
) : OAuthApiClient {

    companion object {
        private const val GRANT_TYPE = "authorization_code"
    }

    override fun oAuthProvider(): OAuthProvider = OAuthProvider.NAVER

    override fun requestAccessToken(params: OAuthLoginParams): String? {

        val url = "$authUrl/oauth2.0/token";  // Token provider url

        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = APPLICATION_FORM_URLENCODED  // Query parameter 형식으로 변환

        val body = params.makeBody()
        body.add("grant_type", GRANT_TYPE)
        body.add("client_id", clientId)
        body.add("client_secret", clientSecret)

        val request = HttpEntity(body, httpHeaders)
        val response: NaverTokens? = restTemplate.postForObject(url, request, NaverTokens::class.java)

        return response?.accessToken
    }

    override fun requestOAuthInfo(accessToken: String?): OAuthInfoResponse? {

        val url = "$apiUrl/v1/nid/me"

        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = APPLICATION_FORM_URLENCODED
        httpHeaders["Authorization"] = "Bearer $accessToken"

        val body: MultiValueMap<String, String> = LinkedMultiValueMap()
        val request = HttpEntity(body, httpHeaders)

        return restTemplate.postForObject(url, request, NaverInfoResponse::class.java)
    }
}