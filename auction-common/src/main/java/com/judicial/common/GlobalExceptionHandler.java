package com.judicial.common;

import org.springframework.web.bind.annotation.*;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BizException.class)
    public ApiResponse<Void> biz(BizException e){ return ApiResponse.fail(400,e.getMessage()); }
    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> all(Exception e){ return ApiResponse.fail(500,e.getMessage()); }
}
