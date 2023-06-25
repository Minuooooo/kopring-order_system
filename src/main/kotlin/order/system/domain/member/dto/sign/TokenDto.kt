package order.system.domain.member.dto.sign


data class TokenDto(
        private val grantType: String,
        private val accessToken: String,
        private val refreshToken: String,
        private val refreshTokenExpiresIn: Long
)
