package order.system.domain.member.dto.sign

import lombok.Builder

@Builder
data class TokenResponseDto(
        private var accessToken: String,
        private var refreshToken: String
)
