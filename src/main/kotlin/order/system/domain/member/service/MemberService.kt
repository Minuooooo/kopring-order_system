package order.system.domain.member.service

import mu.KotlinLogging
import order.system.config.aws.AmazonS3Service
import order.system.config.redis.RedisService
import order.system.domain.member.entity.Member
import order.system.domain.member.repository.MemberRepository
import order.system.exception.situation.MemberNotFoundException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class MemberService(
        private val memberRepository: MemberRepository,
        private val redisService: RedisService,
        private val amazonS3Service: AmazonS3Service
) {

    fun getCurrentMember(): Member =
            memberRepository.findByUsername(SecurityContextHolder.getContext().authentication.name)
                    ?: throw MemberNotFoundException()
}