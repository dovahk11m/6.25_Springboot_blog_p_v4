package com.tenco.blog._core.errors.exception;

//404 NotFound 상황에서 필요한 커스텀 예외 클래스
public class Exception404 extends RuntimeException {

    //요청한 리소스가 없을 때
    public Exception404(String message) {
        super(message);
    }

}
