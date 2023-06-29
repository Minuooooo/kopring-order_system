package order.system.domain.member.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import order.system.config.oauth.param.KakaoLoginParams
import order.system.config.oauth.param.NaverLoginParams
import order.system.domain.member.dto.sign.SignInRequestDto
import order.system.domain.member.dto.sign.SignUpRequestDto
import order.system.domain.member.service.AuthService
import order.system.domain.member.service.MemberService
import order.system.response.Response
import order.system.response.Response.Companion.success
import order.system.response.SuccessMessage
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Auth API Document")
class AuthController(
        private val authService: AuthService,
        private val memberService: MemberService
) {

    @Operation(summary = "Get kakao email API", description = "please sign in kakao.")
    @ResponseStatus(OK)
    @GetMapping("/sign-up/email/kakao")
    fun getKakaoEmail(@RequestBody kakaoLoginParams: KakaoLoginParams): Response =
            success(SuccessMessage.SUCCESS_TO_GET_EMAIL, authService.getUsernameBySocial(kakaoLoginParams))

    @Operation(summary = "Get naver email API", description = "please sign in aver.")
    @ResponseStatus(OK)
    @GetMapping("/sign-up/email/naver")
    fun getNaverEmail(@RequestBody naverLoginParams: NaverLoginParams): Response =
            success(SuccessMessage.SUCCESS_TO_GET_EMAIL, authService.getUsernameBySocial(naverLoginParams))

    @Operation(summary = "Sign up API", description = "put your sign up info.")
    @ResponseStatus(CREATED)
    @PostMapping("/sign-up")
    fun signUp(@Valid @RequestBody signUpRequestDto: SignUpRequestDto): Response {
        authService.signUp(signUpRequestDto)
        return success(SuccessMessage.SUCCESS_TO_SIGN_UP)
    }

    @Operation(summary = "Sign in API", description = "put your sign in info.")
    @ResponseStatus(OK)
    @PostMapping("/sign-in")
    fun signIn(@Valid @RequestBody signInRequestDto: SignInRequestDto): Response {
        return success(SuccessMessage.SUCCESS_TO_SIGN_IN, authService.signIn(signInRequestDto))
    }

    @Operation(summary = "Sign in kakao API", description = "please sign in kakao.")
    @ResponseStatus(OK)
    @PostMapping("/sign-in/kakao")
    fun signInWithKakao(@RequestBody kakaoLoginParams: KakaoLoginParams): Response {
        return success(SuccessMessage.SUCCESS_TO_SIGN_IN, authService.signInWithSocial(kakaoLoginParams))
    }

    @Operation(summary = "Sign in naver API", description = "please sign in naver.")
    @ResponseStatus(OK)
    @PostMapping("/sign-in/naver")
    fun signInWithNaver(@RequestBody naverLoginParams: NaverLoginParams): Response {
        return success(SuccessMessage.SUCCESS_TO_SIGN_IN, authService.signInWithSocial(naverLoginParams))
    }

    @Operation(summary = "Logout API", description = "please logout.")
    @ResponseStatus(OK)
    @PostMapping("/logout")
    fun logout(): Response {
        authService.logout(memberService.getCurrentMember())
        return success(SuccessMessage.SUCCESS_TO_LOGOUT)
    }


}