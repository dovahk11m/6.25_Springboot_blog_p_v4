package com.tenco.blog._core.config;

import com.tenco.blog._core.interceptor.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/* 6.26 인터셉터 등록
WebMvcConfigurer 구현하여 사용할 인터셉터를 등록해준다.
 */
@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    //DI처리
    private final LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(loginInterceptor)
                //인터셉터가 동작할 URI 패턴 지정
                .addPathPatterns("/board/**", "/user/**")
                //인터셉터에서 제외할 URI 패턴 지정
                .excludePathPatterns("/board/{id:\\d+}");//정규표현식 \\d+ 1개 이상의 숫자를 의미






    }
}
