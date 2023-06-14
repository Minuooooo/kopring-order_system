package order.system.exception

import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionAdvice {

    // TODO ExceptionAdvice 도메인 별로 분류 고려

    // 500 에러
    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    fun illegalArgumentException(e: IllegalArgumentException):
}