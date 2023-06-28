package order.system.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("isSuccess", "code", "message", "result")
@Schema(description = "This is response message from server")
data class Response(
        val isSuccess: Boolean,
        val code: Int,
        val message: String,
        val result: Any?
) {
    companion object {
        fun success(message: String): Response = Response(true, OK.value(), message, null);
        fun success(message: String, data: Any): Response = Response(true, OK.value(), message, data);
        fun failure(status: HttpStatus, message: String): Response = Response(false, status.value(), message, null);
    }
}