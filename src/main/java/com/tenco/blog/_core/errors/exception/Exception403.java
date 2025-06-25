package com.tenco.blog._core.errors.exception;

//403 Forbidden 상황에서 필요한 커스텀 예외 클래스
public class Exception403 extends RuntimeException {

    //권한 없음.. 본인이 작성한 게시글이 아님/ 관리자가 아님
    public Exception403(String message) {
        super(message);
    }

}
