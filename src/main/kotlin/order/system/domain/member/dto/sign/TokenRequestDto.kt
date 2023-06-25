package order.system.domain.member.dto.sign


data class TokenRequestDto(
        private val accessToken: String,
        private val refreshToken: String
)
