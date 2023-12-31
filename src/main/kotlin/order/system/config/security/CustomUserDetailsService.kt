package order.system.config.security

import order.system.domain.member.entity.Member
import order.system.domain.member.repository.MemberRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class CustomUserDetailsService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

    // AuthenticationManagerBuilder.getObject().authenticated() 실행할 때 호출
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails =
            memberRepository.findByUsername(username)
                    ?. let { createUserDetails(it) }
                    ?: throw UsernameNotFoundException("$username -> 데이터베이스에서 찾을 수 없습니다.")

    fun createUserDetails(member: Member): UserDetails =
            // password 위치에는 encode 된 값이 있어야 함
            User(member.username, passwordEncoder.encode(member.id.toString()), setOf(SimpleGrantedAuthority(member.authority.toString())))
}