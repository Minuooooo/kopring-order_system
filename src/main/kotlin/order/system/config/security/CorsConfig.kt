package order.system.config.security


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig {
    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()

        config.allowCredentials = true
        config.addAllowedHeader("*")  // 요청 url
        config.addAllowedHeader("*")  // 요청 header
        config.addAllowedMethod("*")  // 요청 HTTP method

        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }
}