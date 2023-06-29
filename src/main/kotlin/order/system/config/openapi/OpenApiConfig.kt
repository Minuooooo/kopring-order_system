package order.system.config.openapi

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun openAPI(): OpenAPI {

        val securitySchemeName = "bearerAuth"

        val info = Info()
                .title("Order API")
                .version("v1.0.0")
                .description("Order API Document")

        return OpenAPI()  // JWT Token 인증 방식 사용
                .addServersItem(Server().url("/"))  // 접근하는 url 과 swagger 에서 API 요청 url 통일
                .addSecurityItem(
                        SecurityRequirement()
                        .addList(securitySchemeName)
                )
                .components(
                        Components()
                        .addSecuritySchemes(
                                securitySchemeName,
                                SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                        )
                )
                .info(info)
    }
}