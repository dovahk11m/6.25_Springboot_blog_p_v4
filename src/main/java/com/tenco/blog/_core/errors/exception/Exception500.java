package com.tenco.blog._core.errors.exception;

//500 Internal Server Error 상황에서 필요한 커스텀 예외 클래스
public class Exception500 extends RuntimeException {

    //DB 오류, DB 연결실패, 파일처리중 오류
    public Exception500(String message) {
        super(message);
    }

}
