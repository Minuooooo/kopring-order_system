package order.system

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource

@SpringBootApplication
@PropertySource("classpath:secure.properties")
class SystemApplication

fun main(args: Array<String>) {
	runApplication<SystemApplication>(*args)
}
