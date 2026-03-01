package com.dart.server.common.advices;

import com.dart.server.common.response.DartApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {
    @Test
    void handleAllExceptions_returnsInternalServerError() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        Exception ex = new Exception("error");
        ResponseEntity<DartApiResponse<Object>> response = handler.handleAllExceptions(ex);
        assertEquals(500, response.getStatusCodeValue());
        assertFalse(response.getBody().isSuccess());
        assertEquals("error", response.getBody().getMessage());
    }
}

