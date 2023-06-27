package order.system.domain.member.entity

import order.system.domain.BaseEntity
import javax.persistence.*

@Entity
class Member(
        var username: String,
        var password: String,
        var name: String,
        @Embedded
        var address: Address,
        var profileImageUrl: String,
        var favorite: Int,
        @Enumerated(EnumType.STRING)
        var authority: Authority
) : BaseEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val id: Long = 0L

    fun changeProfileImageUrl(profileImageUrl: String): String {
        this.profileImageUrl = profileImageUrl
        return this.profileImageUrl
    }

    fun editMember(name: String, address: Address) {
        this.name = name
        this.address = address
    }
}