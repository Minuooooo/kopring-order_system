package order.system.config.oauth.common

interface OAuthApiClient {
    fun oAuthProvider(): OAuthProvider
    fun requestAccessToken(params: OAuthLoginParams): String?
    fun requestOAuthInfo(accessToken: String?): OAuthInfoResponse?
}