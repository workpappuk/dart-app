package com.dart.server.common.advices;

import com.dart.server.common.response.DartApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DartApiResponse<Object>> handleAllExceptions(Exception ex) {
        log.error("An error occurred: ", ex);
        DartApiResponse<Object> response = new DartApiResponse<>(false, ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(jakarta.servlet.ServletException.class)
    public ResponseEntity<DartApiResponse<Object>> handleServletExceptions(jakarta.servlet.ServletException ex) {
        log.error("Servlet error occurred: ", ex);
        DartApiResponse<Object> response = new DartApiResponse<>(false, ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class)
    public ResponseEntity<DartApiResponse<Object>> handleTypeMismatch(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException ex) {
        log.error("Type mismatch error: ", ex);
        DartApiResponse<Object> response = new DartApiResponse<>(false, "Invalid parameter: " + ex.getName(), null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
