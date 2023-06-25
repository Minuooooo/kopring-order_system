package order.system.domain.member.dto.sign


data class TokenResponseDto(
        private val accessToken: String,
        private val refreshToken: String
)
