package com.tenco.blog.board;

import com.tenco.blog._core.errors.exception.Exception401;
import com.tenco.blog._core.errors.exception.Exception403;
import com.tenco.blog._core.errors.exception.Exception404;
import com.tenco.blog.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private static final Logger log = LoggerFactory.getLogger(BoardController.class);

    private final BoardRepository br;

    // 회원정보 수정 화면 요청
    public String updateForm(HttpServletRequest request, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sesseionUser");
        if (sessionUser == null) {
            return "redirect:/login-form";
        }

        request.setAttribute("user", sessionUser);
        return "/user/update-form";
    }

    // 게시글 수정 화면 요청
    @GetMapping("/board/{id}/update-form")
    public String updateForm(@PathVariable(name = "id") Long boardId,
                             HttpServletRequest request, HttpSession session) {

        log.info("게시글 수정 화면 요청 - boardId : {}", boardId);

        User sessionUser = (User) session.getAttribute("sessionUser");

        Board board = br.findById(boardId);
        if (board == null) {
            throw new Exception404("게시글이 존재하지 않습니다");
        }
        if (!board.isOwner(sessionUser.getId())) {
            throw new Exception403("게시글 수정 권한이 없습니다");
        }

        request.setAttribute("board", board);

        return "/board/update-form";
    }

    // 수정 액션 요청
    @PostMapping("/board/{id}/update-form")
    public String update(@PathVariable(name = "id") Long boardId,
                         HttpSession session, BoardRequest.UpdateDTO reqDTO) {

        log.info("게시글 수정 액션 요청 - boardId : {}, 새 제목 {}", boardId, reqDTO.getTitle());

        User sessionUser = (User) session.getAttribute("sessionUser");

        reqDTO.validate();
        Board board = br.findById(boardId);
        if (board == null) {
            throw new Exception404("게시글이 존재하지 않습니다");
        }
        if (!board.isOwner(sessionUser.getId())) {
            throw new Exception403("게시글 수정 권한이 없습니다");
        }

        br.updateById(boardId, reqDTO);

        return "redirect:/board/"+boardId;
    }

    // 게시글 삭제 요청
    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable(name = "id") Long id,HttpSession session ,HttpServletRequest request) {

        log.info("게시글 삭제 요청 - id : {}", id);

        User sessionUser = (User) session.getAttribute("sessionUser");

        Board board = br.findById(id);
        if (board == null) {
            throw new Exception404("게시글이 존재하지 않습니다");
        }
        if (!board.isOwner(sessionUser.getId())) {
            throw new Exception403("게시글 삭제 권한이 없습니다");
        }

        br.deleteById(id);

        return "redirect:/";
    }

    //게시글 작성창 요청
    @GetMapping("/board/save-form")
    public String saveForm(HttpSession session) {

        log.info("게시글 작성창 요청");

        User sessionUser = (User) session.getAttribute("sessionUser");

        return "board/save-form";
    }

    //게시글 저장 액션
    @PostMapping("/board/save")
    public String save(BoardRequest.saveDTO reqDTO, HttpSession session) {

        log.info("게시글 작성 액션 요청 - 제목{}", reqDTO.getTitle());

            User sessionUser = (User) session.getAttribute("sessionUser");

            reqDTO.validate();

            br.save(reqDTO.toEntity(sessionUser));

            return "redirect:/";
    }

    //게시글 상세보기 화면 요청
    @GetMapping("/board/{id}")
    public String detail(@PathVariable(name = "id") Long id, HttpServletRequest request) {

        log.info("상세보기 요청 - id : {}", id);

        Board board = br.findById(id);

        log.info("게시글 상세보기 조회 완료 - 제목 : {}, 작성자 : {}", board.getTitle(), board.getUser());

        request.setAttribute("board", board);

        return "board/detail";
    }

    //메인페이지
    @GetMapping("/")
    public String  index(HttpServletRequest request) {

        log.info("메인페이지 요청");

        List<Board> boardList = br.findByAll();

        log.info("현재 가지고 온 게시글 개수 : {}", boardList.size());

        request.setAttribute("boardList", boardList);

        return "index";
    }
}//BoardController