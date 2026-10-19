package com.judicial.common;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private int code;
    private String msg;
    private T data;
    public static <T> ApiResponse<T> ok(T d){ApiResponse<T> r=new ApiResponse<>();r.code=0;r.msg="ok";r.data=d;return r;}
    public static ApiResponse<Void> ok(){return ok(null);}
    public static <T> ApiResponse<T> fail(int c,String m){ApiResponse<T> r=new ApiResponse<>();r.code=c;r.msg=m;return r;}
}
