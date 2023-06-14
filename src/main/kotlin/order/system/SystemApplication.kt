package order.system

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SystemApplication

fun main(args: Array<String>) {
	runApplication<SystemApplication>(*args)
}
