package order.system.exception

import mu.KotlinLogging
import order.system.exception.situation.*
import order.system.response.Response
import order.system.response.Response.Companion.failure
import org.springframework.http.HttpStatus.*
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.*
import javax.management.relation.RoleNotFoundException

private val logger = KotlinLogging.logger {}

@RestControllerAdvice
class ExceptionAdvice {

    // TODO ExceptionAdvice 도메인 별로 분류 고려

    // 500 에러
    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    fun illegalArgumentExceptionAdvice(e: IllegalArgumentException): Response {
        logger.info("e={}", e.message)
        return failure(INTERNAL_SERVER_ERROR, e.message ?: "");
    }

    // 500 에러
    // 컨버트 실패
    @ExceptionHandler(CannotConvertHelperException::class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    fun cannotConvertNestedStructureException(e: CannotConvertHelperException): Response {
        logger.info("e={}", e.message)
        return failure(INTERNAL_SERVER_ERROR, e.message ?: "")
    }

    // 400 에러
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(BAD_REQUEST)
    fun methodArgumentNotValidException(e: MethodArgumentNotValidException): Response {
        return failure(BAD_REQUEST, e.bindingResult.fieldErrors.firstOrNull()?.defaultMessage ?: "");
    }

    // 400 에러
    @ExceptionHandler(TokenExpiredException::class)
    @ResponseStatus(BAD_REQUEST)
    fun tokenExpiredException(): Response {
        return failure(BAD_REQUEST, "토큰이 만료되었습니다.")
    }

    // 400 에러
    @ExceptionHandler(BindException::class)
    @ResponseStatus(BAD_REQUEST)
    fun bindException(e: BindException): Response {
        return failure(BAD_REQUEST, e.bindingResult.fieldErrors.firstOrNull()?.defaultMessage ?: "")
    }

    // 401 응답
    // 아이디 혹은 비밀번호 오류 시 발생
    @ExceptionHandler(LoginFailureException::class)
    @ResponseStatus(UNAUTHORIZED)
    fun loginFailureException(): Response {
        return failure(UNAUTHORIZED, "로그인에 실패하였습니다.")
    }

    // 401 응답
    // 요청자와 요청한 유저의 정보가 일치하지 않을 시 발생
    @ExceptionHandler(MemberNotEqualsException::class)
    @ResponseStatus(UNAUTHORIZED)
    fun memberNotEqualsException(): Response {
        return failure(UNAUTHORIZED, "유저 정보가 일치하지 않습니다.")
    }

    // 404 응답
    // 요청한 유저를 찾을 수 없음
    @ExceptionHandler(MemberNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    fun memberNOtFoundException(): Response {
        return failure(NOT_FOUND, "요청한 회원을 찾을 수 없습니다.")
    }

    // 404 응답
    // 요청한 자원을 찾을 수 없음
    @ExceptionHandler(RoleNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    fun roleNotFoundException(): Response {
        return failure(NOT_FOUND, "요청한 권한 등급을 찾을 수 없습니다.")
    }

    // 409 응답
    // username 중복
    @ExceptionHandler(UsernameAlreadyExistsException::class)
    @ResponseStatus(CONFLICT)
    fun usernameAlreadyExistsException(e: UsernameAlreadyExistsException): Response {
        return failure(CONFLICT, e.message + "은 중복된 아이디 입니다.")
    }

    // 404 응답
    // Image 형식 지원하지 않음
    @ExceptionHandler(UnsupportedImageFormatException::class)
    @ResponseStatus(NOT_FOUND)
    fun unsupportedImageFormatException(): Response {
        return failure(NOT_FOUND, "이미지 형식을 지원하지 않습니다.")
    }

    // 404 응답
    // 파일 업로드 실패
    @ExceptionHandler(FileUploadFailureException::class)
    @ResponseStatus(NOT_FOUND)
    fun fileUploadFailureException(e: FileUploadFailureException): Response {
        logger.error("e = {}", e.message)
        return failure(NOT_FOUND, "이미지 업로드 실패")
    }

    // 404 응답
    // 파일이 비어있음
    @ExceptionHandler(EmptyFileException::class)
    @ResponseStatus(NOT_FOUND)
    fun emptyFileException(): Response {
        return failure(NOT_FOUND, "파일이 비어있습니다.")
    }

    // 400 응답
    // 파일 확장자가 존재하지 않음
    @ExceptionHandler(StringIndexOutOfBoundsException::class)
    @ResponseStatus(BAD_REQUEST)
    fun stringIndexOutOfBoundsException(e: StringIndexOutOfBoundsException): Response {
        logger.info("error= {}", e.message)
        return failure(BAD_REQUEST, "파일 확장자가 존재하지 않습니다.")
    }

    // 400 응답
    // 기본 이미지로 변경할 수 없음
    @ExceptionHandler(AlreadyBasicException::class)
    @ResponseStatus(BAD_REQUEST)
    fun alreadyBasicException(): Response {
        return failure(BAD_REQUEST, "기본 이미지로 변경할 수 없습니다.")
    }

    // 404 응답
    // 요청한 물품을 찾을 수 없음
    @ExceptionHandler(ItemNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    fun itemNotFoundException(): Response {
        return failure(NOT_FOUND, "요청한 물품을 찾을 수 없습니다.")
    }

    // 400 응답
    // 요청한 수량이 상품의 재고를 초과함
    @ExceptionHandler(StockQuantityExcessException::class)
    @ResponseStatus(BAD_REQUEST)
    fun stockQuantityExcessException(): Response {
        return failure(BAD_REQUEST, "요청한 수량이 상품의 재고를 초과합니다.")
    }

    // 404 응답
    // 요청한 주문 물품을 찾을 수 없음
    @ExceptionHandler(OrderItemNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    fun orderItemNotFoundException(): Response {
        return failure(NOT_FOUND, "요청한 주문 물품을 찾을 수 없습니다.")
    }

    // 404 응답
    // 요청한 주문을 찾을 수 없음
    @ExceptionHandler(OrderNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    fun orderNotFoundException(): Response {
        return failure(NOT_FOUND, "요청한 주문을 찾을 수 없습니다.")
    }

    // 400 응답
    // 주문 정보를 수정할 수 없음
    @ExceptionHandler(CannotEditOrderInfoException::class)
    @ResponseStatus(BAD_REQUEST)
    fun cannotEditOrderInfoException(): Response {
        return failure(BAD_REQUEST, "주문 정보를 수정할 수 없습니다.")
    }

    // 404 응답
    // 요청한 채팅방을 찾을 수 없음
    @ExceptionHandler(ChatRoomNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    fun chatRoomNotFoundException(): Response {
        return failure(NOT_FOUND, "요청한 채팅방을 찾을 수 없습니다.")
    }

    // 404 응답
    // 요청한 채팅 메시지를 찾을 수 없음
    @ExceptionHandler(ChatMessageNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    fun chatMessageNotFoundException(): Response {
        return failure(NOT_FOUND, "요청한 채팅 메시지를 찾을 수 없습니다.")
    }
}