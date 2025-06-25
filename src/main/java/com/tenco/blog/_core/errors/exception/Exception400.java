package com.tenco.blog._core.errors.exception;

public class Exception400 extends RuntimeException {

    //에러 메세지로 사용할 문자열을 super 클래스에게 전달
    public Exception400(String message) {
        super(message);
    }
    //예시 throw new Exception400("잘못된 요청입니다");
    //필수 입력 항목이 누락됐거나 올바르지 않은 데이터 형식이 들어오면 (400)

}
