package order.system.config.security

import order.system.config.jwt.JwtAccessDeniedHandler
import order.system.config.jwt.JwtAuthenticationEntryPoint
import order.system.config.jwt.JwtFilter
import order.system.config.jwt.JwtProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.filter.CorsFilter
import kotlin.jvm.Throws

@Configuration
class SecurityConfig(
        private val jwtProvider: JwtProvider,
        private val corsFilter: CorsFilter,
        private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
        private val jwtAccessDeniedHandler: JwtAccessDeniedHandler
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    @Throws(Exception::class)
    protected fun config(http: HttpSecurity): SecurityFilterChain =
            http
                    .csrf().disable()

                    .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter::class.java)
                    .addFilterBefore(JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter::class.java)

                    .sessionManagement {
                        it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    }

                    .exceptionHandling {
                        it
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                    }

                    .authorizeHttpRequests{
                        it
                                .antMatchers("/swagger-ui/**", "/api-docs/**", "/v1/**", "/api")  // 인증하지 않을 url
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                    }

                    .build()
}