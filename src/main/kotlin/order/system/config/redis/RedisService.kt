package order.system.config.redis

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class RedisService(private val redisTemplate: RedisTemplate<String, String>) {

    fun setValues(key: String, data: String, duration: Duration) = redisTemplate.opsForValue().set(key, data, duration)
    fun getValues(key: String): String? = redisTemplate.opsForValue()[key]
    fun deleteValues(key: String) = redisTemplate.delete(key)
}