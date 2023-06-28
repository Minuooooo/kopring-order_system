package order.system.domain.member.service

import mu.KotlinLogging
import order.system.config.jwt.JwtProvider
import order.system.config.oauth.RequestOAuthInfoService
import order.system.config.oauth.common.OAuthLoginParams
import order.system.config.redis.RedisService
import order.system.domain.member.dto.sign.*
import order.system.domain.member.entity.Member
import order.system.domain.member.repository.MemberRepository
import order.system.exception.situation.MemberNotFoundException
import order.system.exception.situation.UsernameAlreadyExistsException
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.time.Duration
import java.util.*

private val logger = KotlinLogging.logger {}

@Service
class AuthService(
        private val memberRepository: MemberRepository,
        private val passwordEncoder: PasswordEncoder,
        private val authenticationManagerBuilder: AuthenticationManagerBuilder,
        private val redisService: RedisService,
        private val jwtProvider: JwtProvider,
        private val requestOAuthInfoService: RequestOAuthInfoService
) {

    fun getUsernameBySocial(oAuthLoginParams: OAuthLoginParams): SignInResponseDtoBySocial =
            SignInResponseDtoBySocial(requestOAuthInfoService.request(oAuthLoginParams)?.getEmail())

    fun signUp(req: SignUpRequestDto) {
        validateSignUpInfo(req.username)  // TODO 소셜 로그인 계정이 이미 있을 때 가입할 경우 휴대전화 인증을 통해 계정 존재 유무 알려주기
        memberRepository.save(req.toEntity(passwordEncoder))
    }

    fun signIn(req: SignInRequestDto): TokenResponseDto =
            authorize(
                    req,
                    memberRepository.findByUsername(req.username)
                            ?.id
                            ?: throw MemberNotFoundException()
            )

    fun logout(member: Member) {
        redisService.deleteValues("RT: ${member.username}")
    }

    private fun validateSignUpInfo(usernameToValidate: String) {
        if (memberRepository.existsByUsername(usernameToValidate))
            throw UsernameAlreadyExistsException(usernameToValidate)
    }

    private fun authorize(signInRequestDto: SignInRequestDto, memberId: Long): TokenResponseDto {
        val authentication: Authentication = authenticationManagerBuilder.`object`.authenticate(signInRequestDto.toAuthentication(memberId))
        val foundRefreshToken: String? = redisService.getValues("RT: ${signInRequestDto.username}")

        if (!StringUtils.hasText(foundRefreshToken)) {  // 로그인한 유저의 refresh token 이 존재하지 않거나 만료되었을 때
            val tokenDto: TokenDto = getReadyForAuthorize(authentication)
            return TokenResponseDto(tokenDto.accessToken, tokenDto.refreshToken)
        }

        return TokenResponseDto(jwtProvider.generateAccessToken(authentication, Date().time), foundRefreshToken)  // 로그인한 유저의 refresh token 이 존재할 때
    }

    private fun getReadyForAuthorize(authentication: Authentication): TokenDto {
        val tokenDto: TokenDto = jwtProvider.generateTokenDto(authentication)
        redisService.setValues(
                "RT: ${authentication.name}",
                tokenDto.refreshToken,
                Duration.ofMillis(tokenDto.refreshTokenExpiresIn)
        )
        return tokenDto
    }
}