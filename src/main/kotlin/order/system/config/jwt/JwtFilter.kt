package order.system.config.jwt

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.jvm.Throws

class JwtFilter(private val jwtProvider: JwtProvider) : OncePerRequestFilter() {

    companion object {
        const val AUTHENTICATION_HEADER: String = "Authorization"
        const val BEARER_PREFIX: String = "Bearer "
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        // 1. Request Header 에서 토큰을 꺼냄
        val jwt = resolveToken(request)

        // 2. validateToken 으로 토큰 유효성 검사
        // 정상 토큰이면 해당 토큰으로 Authentication 을 가져와서 SecurityContext 에 저장
        if (jwt != "nothing" && jwtProvider.validateToken(jwt))
            SecurityContextHolder.getContext().authentication = jwtProvider.getAuthentication(jwt)

        filterChain.doFilter(request, response)
    }

    // Request Header 에서 토큰 정보를 꺼내오기
    private fun resolveToken(request: HttpServletRequest): String {
        val bearerToken = request.getHeader(AUTHENTICATION_HEADER)

        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            bearerToken.substring(7)
        } else {
            "nothing"
        }
    }
}