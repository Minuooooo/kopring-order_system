package order.system.domain.member.dto.sign

data class TokenDto(
        val grantType: String,
        val accessToken: String,
        val refreshToken: String,
        val refreshTokenExpiresIn: Long
)

data class TokenRequestDto(
        val accessToken: String,
        val refreshToken: String
)

data class TokenResponseDto(
        val accessToken: String,
        val refreshToken: String?
)