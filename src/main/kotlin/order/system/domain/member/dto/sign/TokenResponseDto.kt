package order.system.domain.member.dto.sign


data class TokenResponseDto(
        private var accessToken: String,
        private var refreshToken: String
)
