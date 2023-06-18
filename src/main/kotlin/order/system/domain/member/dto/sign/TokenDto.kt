package order.system.domain.member.dto.sign


data class TokenDto(
        private var grantType: String,
        private var accessToken: String,
        private var refreshToken: String,
        private var refreshTokenExpiresIn: Long
)
