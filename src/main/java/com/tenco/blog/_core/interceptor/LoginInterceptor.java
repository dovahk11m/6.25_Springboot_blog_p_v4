package com.tenco.blog._core.interceptor;

import com.tenco.blog._core.errors.exception.Exception401;
import com.tenco.blog.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/* 6.26 인터셉터
로그인 인터셉터
(인증검사 & 분기처리)
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * preHandle 😎 컨트롤러에 들어가기 전에 동작하는 메서드
     * 리턴타입이 boolean
     * true 컨트롤러로 진입, false 못들어감
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        //클라이언트가 던진 세션 키값을 와스에서 조회한다

        System.out.println("인터셉터 동작 확인" + request.getRequestURL());

        User sessionUser = (User) session.getAttribute("sessionUser");
        //조회된 세션 키값을 sessionUser에 담는다

        if (sessionUser == null) {
            throw new Exception401("로그인이 필요합니다 로그인 해주세요");
            //return false;
        }
        return true; //분기처리
    }

    /**
     * postHandle 🙂 뷰가 렌더링 되기 '전'에 콜백되는 메서드
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * afterCompletion 😶 뷰가 완전 렌더링 된 '뒤' 호출될 수 있다
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
