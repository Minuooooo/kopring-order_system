package order.system.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass  // JPA Entity 클래스들이 BaseTimeEntity 를 상속하는 경우, 필드(createdAt, modifiedAt)들도 모두 컬럼으로 인식하도록 한다,
@EntityListeners(AuditingEntityListener::class)  // BastTimeEntity 클래스에 시간 데이터를 자동으로 매핑하여 값을 넣어주는 JPA Auditing 기능 포함
abstract class BaseEntity {

    @CreatedDate  // Entity 가 생성되어 저장될 때 시간 자동 저장
    val createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate  // 조회한 Entity 의 값을 변경할 때 최종 수정 시간 자동 저장
    var updatedAt: LocalDateTime? = null

    @LastModifiedDate
    var deletedAt: LocalDateTime? = null
}