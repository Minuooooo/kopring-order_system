package order.system.config.jwt

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {
    @Throws(IOException::class, ServletException::class)
    override fun commence(request: HttpServletRequest?, response: HttpServletResponse?, authException: AuthenticationException?) {
        // 유효한 자격 증명을 제공하지 않고 접근하려 할 때 401
        response?.sendError(HttpServletResponse.SC_UNAUTHORIZED)
    }
}