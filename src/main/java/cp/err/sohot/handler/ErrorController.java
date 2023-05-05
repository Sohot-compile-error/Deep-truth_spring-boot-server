package cp.err.sohot.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cp.err.sohot.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto<?>> handleException(Exception e) {

        log.error("[handleException] 특정 되지 않은 예외");
        ErrorResponseDto<Object> errorResponseDto = ErrorResponseDto.builder()
            .message(e.getMessage())
            .build();
        return ResponseEntity.internalServerError()
            .body(errorResponseDto);
    }
}
