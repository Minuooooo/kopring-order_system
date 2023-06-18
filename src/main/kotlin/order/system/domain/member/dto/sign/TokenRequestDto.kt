package order.system.domain.member.dto.sign


data class TokenRequestDto(
        private var accessToken: String,
        private var refreshToken: String
)
