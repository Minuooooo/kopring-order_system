package order.system.config.websocket

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig : WebSocketMessageBrokerConfigurer {

    // sockJs Fallback 을 이용해 노출할 endpoint 설정
    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        // 웹 소켓이 handshake 를 하기 위해 연결하는 endpoint
        registry
                .addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS()
    }

    // 메세지 브로커에 관한 설정
    override fun configureMessageBroker(registry: MessageBrokerRegistry) {

        // Server -> client 발행하는 메세지에 대한 endpoint 설정: 구독
        registry.enableSimpleBroker("/sub")

        // Client -> server 발행하는 메세지에 대한 endpoint 설정: 구독에 대한 메세지
        registry.setApplicationDestinationPrefixes("/pub")
    }
}