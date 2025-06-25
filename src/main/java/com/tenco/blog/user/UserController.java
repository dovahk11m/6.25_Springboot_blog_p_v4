package com.tenco.blog.user;

import com.tenco.blog._core.errors.exception.Exception400;
import com.tenco.blog._core.errors.exception.Exception401;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor //DI
@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserRepository ur;
    private final HttpSession httpSession;

    //회원정보 수정화면 요청
    @GetMapping("/user/update-form")
    public String updateForm(HttpServletRequest request, HttpSession hs) {

        log.info("회원정보 수정화면 요청");

        User sUser = (User) hs.getAttribute("sessionUser");
        if (sUser == null) {
            return "redirect:/login-form";
        }
        request.setAttribute("user", sUser);
        return "user/update-form";
    }

    //회원정보 수정 처리
    @PostMapping("/user/update")
    public String update(UserRequest.UpdateDTO reqDTO,
                         HttpSession session, HttpServletRequest request) {

        log.info("게시글 수정 요청");

        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new Exception401("로그인이 필요한 서비스입니다 로그인하세요");
        }
        reqDTO.validate();
        User updateUser = ur.updateById(sessionUser.getId(), reqDTO);

        //세션 동기화
        session.setAttribute("sesstionUser", updateUser);

        return "redirect:/user/update-form";
    }

    //회원가입 화면 요청
    @GetMapping("/join-form")
    public String joinForm() {

        log.info("회원가입 화면 요청");

        return "user/join-form";
    }

    //회원가입 처리
    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO, HttpServletRequest request) {

        log.info("회원가입 처리");
        log.info("회원명: {} ", joinDTO.getUsername());
        log.info("회원메일: {}", joinDTO.getEmail());

        joinDTO.validate();

        User existUser = ur.findByUsername(joinDTO.getUsername());
        if (existUser != null) {
            throw new Exception401("이미 존재하는 회원명: " + joinDTO.getUsername());
        }

        User user = joinDTO.toEntity();

        ur.save(user);

        return "redirect:/login-form";
    }//join

    //로그인 화면 요청
    @GetMapping("/login-form")
    public String loginForm() {

        log.info("로그인 화면 요청");

        return "user/login-form";
    }

    //로그인 처리
    @PostMapping("/login")
    public String login(UserRequest.LoginDTO loginDTO) {
        log.info("로그인 시도 발생");
        log.info("회원명: {}", loginDTO.getUsername());
        loginDTO.validate();
        User user = ur.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());
        if (user == null) {
            throw new Exception400("잘못된 입력입니다");
        }
        httpSession.setAttribute("sessionUser", user);

        return "redirect:/";
    }

    //로갓 요청
    @GetMapping("/logout")
    public String logout() {
        httpSession.invalidate();
        return "redirect:/";
    }



}//UserController
