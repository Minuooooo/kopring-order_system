package order.system.domain.member.dto.sign

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import order.system.domain.member.entity.Address
import order.system.domain.member.entity.Authority
import order.system.domain.member.entity.Member
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.util.StringUtils
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class SignUpRequestDto(
        @field:NotBlank(message = "이메일을 입력해주세요.")
        @field:Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", message = "이메일은 @를 포함해야 합니다.") // 이메일 형식
        @field:Schema(description = "이메일", defaultValue = "test@gmail.com")
        val username: String,

        @field:NotBlank(message = "비밀번호를 입력해주세요.")
        @field:Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "비밀번호는 최소 8자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.") // 비밀번호 형식
        @field:Schema(description = "비밀번호", defaultValue = "test1234!")
        val password: String,

        @field:NotBlank(message = "사용자 이름을 입력해주세요.")
        @field:Size(min = 2, message = "사용자 이름이 너무 짧습니다.")
        @field:Schema(description = "이름", defaultValue = "홍길동")
        val name: String,

        @field:NotBlank(message = "시,도,군을 입력해주세요.")
        @field:Schema(description = "시,도,군", defaultValue = "경기도")
        val city: String,

        @field:NotBlank(message = "도로명을 입력해주세요.")
        @field:Schema(description = "도로명", defaultValue = "고양이 네로")
        val street: String,

        @field:NotBlank(message = "우편번호를 입력해주세요.")
        @field:Pattern(regexp = "^\\d{5}$", message = "우편번호는 5자리이어야 합니다.") // 우편번호 형식
        @field:Schema(description = "우편번호", defaultValue = "11111")
        val zipcode: String
) {
    fun toEntity(passwordEncoder: PasswordEncoder): Member =
            Member(
                    username,
                    passwordEncoder.encode(validateExistsByPassword(passwordEncoder)),  // 일반, 소셜 회원가입을 비밀번호 유무로 구분
                    name,
                    Address(city, street, zipcode),
                    "basic_profile.png",
                    0,
                    Authority.ROLE_USER
            )

    private fun validateExistsByPassword(passwordEncoder: PasswordEncoder): String =  // 비밀번호가 있다면 일반, 없다면 소셜 회원가입
            if (!StringUtils.hasText(password))
                passwordEncoder.encode(UUID.randomUUID().toString())
            else
                passwordEncoder.encode(password)
}

data class SignInRequestDto(
        @field:NotBlank(message = "이메일을 입력해주세요.")
        @field:Schema(description = "이메일", defaultValue = "test@gmail.com")
        val username: String,

        @field:NotBlank(message = "비밀번호를 입력해주세요.")
        @field:Schema(description = "비밀번호", defaultValue = "test1234!")
        val password: String
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SignInResponseDtoBySocial(val email: String?)