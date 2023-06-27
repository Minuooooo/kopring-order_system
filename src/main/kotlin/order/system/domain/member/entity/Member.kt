package order.system.domain.member.entity

import order.system.domain.BaseEntity
import javax.persistence.*

@Entity
class Member(
        private var username: String,
        private var password: String,
        private var name: String,
        @Embedded
        private var address: Address,
        private var profileImageUrl: String,
        private var favorite: Int,
        @Enumerated(EnumType.STRING)
        private var authority: Authority
) : BaseEntity() {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private val id: Long = 0L
}