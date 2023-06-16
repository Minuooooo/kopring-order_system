package order.system.config.jwt

import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key

private val logger = KotlinLogging.logger {}

@Component
class JwtProvider(@Value("\${jwt.secret}") secretKey: String) {
    companion object {
        private const val AUTHORITIES_KEY: String = "auth"
        private const val BEARER_TYPE: String = "bearer"
        private const val ACCESS_TOKEN_EXPIRE_TIME: Long = 1000 * 60 * 60 * 24;  // 1시간 * 24 = 24시간 (-Dev)
        private const val REFRESH_TOKEN_EXPIRE_TIME: Long = 1000 * 60 * 60 * 24 * 7;  // 7일
    }

    private val key: Key

    init {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
    }
}