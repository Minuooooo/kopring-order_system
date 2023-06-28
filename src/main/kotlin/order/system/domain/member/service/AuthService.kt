package order.system.domain.member.service

import mu.KotlinLogging
import order.system.config.jwt.JwtProvider
import order.system.config.oauth.RequestOAuthInfoService
import order.system.config.oauth.common.OAuthLoginParams
import order.system.config.redis.RedisService
import order.system.domain.member.dto.sign.SignInResponseDtoBySocial
import order.system.domain.member.dto.sign.SignUpRequestDto
import order.system.domain.member.repository.MemberRepository
import order.system.exception.situation.UsernameAlreadyExistsException
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

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

    fun findUsernameBySocial(oAuthLoginParams: OAuthLoginParams): SignInResponseDtoBySocial =
            SignInResponseDtoBySocial(requestOAuthInfoService.request(oAuthLoginParams)?.getEmail())

    fun signUp(req: SignUpRequestDto) {
        validateSignUpInfo(req.username)  // TODO 소셜 로그인 계정이 이미 있을 때 가입할 경우 휴대전화 인증을 통해 계정 존재 유무 알려주기
        memberRepository.save(req.toEntity(passwordEncoder))
    }

    private fun validateSignUpInfo(usernameToValidate: String) {
        if (memberRepository.existsByUsername(usernameToValidate))
            throw UsernameAlreadyExistsException(usernameToValidate)
    }
}