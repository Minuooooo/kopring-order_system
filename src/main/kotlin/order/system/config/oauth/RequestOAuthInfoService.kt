package order.system.config.oauth

import order.system.config.oauth.common.OAuthApiClient
import order.system.config.oauth.common.OAuthInfoResponse
import order.system.config.oauth.common.OAuthLoginParams
import order.system.config.oauth.common.OAuthProvider
import org.springframework.stereotype.Component

@Component
class RequestOAuthInfoService(private val oAuthApiClients: List<OAuthApiClient>) {

    private val clients: Map<OAuthProvider, OAuthApiClient> = oAuthApiClients.associateBy(OAuthApiClient::oAuthProvider)

    fun request(params: OAuthLoginParams): OAuthInfoResponse? {
        val client = clients[params.oAuthProvider()]
        return client?.requestOAuthInfo(client.requestAccessToken(params))
    }
}