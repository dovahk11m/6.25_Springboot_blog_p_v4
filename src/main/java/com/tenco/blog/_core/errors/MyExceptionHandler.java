package com.tenco.blog._core.errors;

import com.tenco.blog._core.errors.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/* @ControllerAdvice
모든 컨트롤러에서 발생하는 예외처리를 이 클래스에서 처리한다
RuntimeException 발생하면 해당 파일로 예외처리가 집중된다.
 */
@ControllerAdvice
//@RestControllerAdvice //데이터를 반환해주고 싶을때
public class MyExceptionHandler {

    //slf4j 로거 생성 - 👍로깅 사용시 sysout 대신 사용하면 좋다!
    private static final Logger log = LoggerFactory.getLogger(MyExceptionHandler.class);

    @ExceptionHandler
    public String ex400(Exception400 e, HttpServletRequest request) {

        log.warn("💀 400 Bad Request 에러 발생");
        log.warn("요청URL : {}", request.getRequestURL());
        log.warn("인증 오류: {}", e.getMessage());
        log.warn("User-Agent: {}", request.getHeader("User-Agent"));

        request.setAttribute("msg", e.getMessage());
        return "err/400";
    }

    @ExceptionHandler
    public String ex401(Exception401 e, HttpServletRequest request) {

        log.warn("💀 401 Unauthorized 에러 발생");
        log.warn("요청URL : {}", request.getRequestURL());
        log.warn("인증 오류: {}", e.getMessage());
        log.warn("User-Agent: {}", request.getHeader("User-Agent"));

        request.setAttribute("msg", e.getMessage());
        return "err/401";
    }

    @ExceptionHandler // 403이 터지면 이 메서드를 호출하라
    public String ex403(Exception403 e, HttpServletRequest request) {

        log.warn("💀 403 Forbidden 에러 발생");
        log.warn("요청URL : {}", request.getRequestURL());
        log.warn("인증 오류: {}", e.getMessage());
        log.warn("User-Agent: {}", request.getHeader("User-Agent"));

        request.setAttribute("msg", e.getMessage());
        return "err/403";
    }
    /* 단순한 데이터바인딩에는 모델을 써도 된다.
     */

    @ExceptionHandler
    public String ex404(Exception404 e, HttpServletRequest request) {

        log.warn("💀 404 NotFound 에러 발생");
        log.warn("요청URL : {}", request.getRequestURL());
        log.warn("인증 오류: {}", e.getMessage());
        log.warn("User-Agent: {}", request.getHeader("User-Agent"));

        request.setAttribute("msg", e.getMessage());
        return "err/404";
    }

    @ExceptionHandler
    public String ex500(Exception500 e, HttpServletRequest request) {

        log.warn("💀 500 InternalServerError 에러 발생");
        log.warn("요청URL : {}", request.getRequestURL());
        log.warn("인증 오류: {}", e.getMessage());
        log.warn("User-Agent: {}", request.getHeader("User-Agent"));

        request.setAttribute("msg", e.getMessage());
        return "err/500";
    }

    //기타 모든 RuntimeException 처리
    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException e, HttpServletRequest request) {

        log.warn("💀 예상치 못한 런타임 에러 발생");
        log.warn("요청URL : {}", request.getRequestURL());
        log.warn("인증 오류: {}", e.getMessage());
        log.warn("User-Agent: {}", request.getHeader("User-Agent"));

        request.setAttribute("msg", "시스템 오류 발생, 관리자에게 문의");
        return "err/500";
    }
}
