package order.system.domain.member.repository

import order.system.domain.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByUsername(username: String?): Member?
    fun existsByUsername(username: String): Boolean
}