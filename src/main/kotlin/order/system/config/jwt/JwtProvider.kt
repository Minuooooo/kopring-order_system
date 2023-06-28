package order.system.config.jwt

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import mu.KotlinLogging
import order.system.domain.member.dto.sign.TokenDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date

private val log = KotlinLogging.logger {}

@Component
class JwtProvider(
        @Value("\${jwt.secret}")
        private val secretKey: String
) {
    companion object {
        private const val AUTHORITIES_KEY: String = "auth"
        private const val BEARER_TYPE: String = "bearer"
        private const val ACCESS_TOKEN_EXPIRE_TIME: Long = 1000 * 60 * 60 * 24;  // 1시간 * 24 = 24시간 (-Dev)
        private const val REFRESH_TOKEN_EXPIRE_TIME: Long = 1000 * 60 * 60 * 24 * 7;  // 7일
    }

    private val key: Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))

    fun generateTokenDto(authentication: Authentication): TokenDto {

        val now = Date().time
        val accessTokenExpiresIn = Date(now + ACCESS_TOKEN_EXPIRE_TIME)
        val refreshTokenExpiresIn = Date(now + REFRESH_TOKEN_EXPIRE_TIME)

        return TokenDto(
                grantType = BEARER_TYPE,
                accessToken = generateAccessToken(authentication, accessTokenExpiresIn),
                refreshToken = generateRefreshToken(refreshTokenExpiresIn),
                refreshTokenExpiresIn = refreshTokenExpiresIn.time
        )
    }

    fun getAuthentication(accessToken: String): Authentication {
        // 토큰 복호화
        val claims = parseClaims(accessToken)

        if (claims[AUTHORITIES_KEY] == null) {
            throw RuntimeException("권한 정보가 없는 토큰입니다.")
        }

        // 클레임에서 권한 정보 가져오기
        val authorities: Collection<GrantedAuthority> = claims[AUTHORITIES_KEY].toString().split(",")
                .map { SimpleGrantedAuthority(it) }
                .toList()

        // userDetails 객체를 만들어서 Authentication 리턴
        return UsernamePasswordAuthenticationToken(
                User(claims.subject, "", authorities),
                "",
                authorities
        )
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (e: SecurityException) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (e: MalformedJwtException) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (e: ExpiredJwtException) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (e: UnsupportedJwtException) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (e: IllegalArgumentException) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    fun extractSubject(accessToken: String): String = parseClaims(accessToken).subject

    fun generateAccessToken(authentication: Authentication, accessTokenExpiresIn: Date): String {
        // 권한들 가져오기
        val authorities = authentication.authorities
                .joinToString(",") { it.authority }

        // Access Token 생성
        return Jwts.builder()
                .setSubject(authentication.name)            // payload "sub": "name"
                .claim(AUTHORITIES_KEY, authorities)        // payload "auth": "ROLE_USER"
                .setExpiration(accessTokenExpiresIn)        // payload "exp": 1516239022
                .signWith(key, SignatureAlgorithm.HS512)    // header "alg": "HS512"
                .compact()
    }

    fun generateRefreshToken(refreshTokenExpiresIn: Date): String =
            Jwts.builder()
                    .setExpiration(refreshTokenExpiresIn)
                    .signWith(key, SignatureAlgorithm.HS512)
                    .compact();

    private fun parseClaims(accessToken: String): Claims =
            try {
                Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).body;
            } catch (e: ExpiredJwtException) {
                e.claims
            }
}