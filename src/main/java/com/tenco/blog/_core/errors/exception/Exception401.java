package com.tenco.blog._core.errors.exception;

//401 Unauthorized 상황에서 필요한 커스텀 예외 클래스
public class Exception401 extends RuntimeException {

    //로그인이 필요, 세션이 만료
    public Exception401(String message) {
        super(message);
    }

}
