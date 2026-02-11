package com.dart.server.common.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DartApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

}