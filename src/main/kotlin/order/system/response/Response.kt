package order.system.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("isSuccess", "code", "message", "result")
@Schema(description = "This is response message from server")
data class Response(  // TODO code, data 만을 활용해 custom response 구현
        private val isSuccess: Boolean,
        private val code: Int,
        private val message: String,
        private val data: Any?
) {
    companion object {
        fun success(message: String): Response = Response(true, OK.value(), message, null);
        fun success(message: String, data: Any): Response = Response(true, OK.value(), message, data);
        fun failure(status: HttpStatus, message: String): Response = Response(false, status.value(), message, null);
    }
}