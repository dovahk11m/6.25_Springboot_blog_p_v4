package com.tenco.blog.board;

import com.tenco.blog._core.errors.exception.Exception403;
import com.tenco.blog._core.errors.exception.Exception404;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardRepository {

    private static final Logger log = LoggerFactory.getLogger(BoardRepository.class);
    private final EntityManager em;

    //수정 요청 feat.더티체킹
    @Transactional
    public Board updateById(Long id, BoardRequest.UpdateDTO reqDTO) {

        log.info("게시글 수정 시작 - id : {}", id);

        Board board = findById(id);
        board.setTitle(reqDTO.getTitle());
        board.setContent(reqDTO.getContent());
        return board;
    }

    //게시글 삭제
    @Transactional
    public void deleteById(Long id) {

        log.info("게시글 수정 시작 - id : {}", id);

        String jpql = "DELETE FROM Board b WHERE b.id = :id ";
        Query query = em.createQuery(jpql);
        query.setParameter("id", id);

        int deletedCount = query.executeUpdate();
        if (deletedCount == 0) {
            throw new Exception404("삭제할 게시글이 없습니다");
        }
        log.info("게시글 삭제 완료 - 삭제 행 수: {}", deletedCount);
    }

    //게시글 삭제
    @Transactional
    public void deleteByIdSafely(Long id) {
        Board board = em.find(Board.class, id);
        if (board == null) {
            throw new Exception404("삭제할 게시글이 없습니다");
        }

        em.remove(board);
    }

    //게시글 저장
    @Transactional
    public Board save(Board board) {

        log.info("게시글 저장 시작 - 제목 : {}, 작성자 : {}", board.getTitle(), board.getUser());

        em.persist(board);
        return board;
    }


    //게시글 1건 조회
    public Board findById(Long id) {

        log.info("게시글 1건조회 시작");

        Board board = em.find(Board.class, id);
        return board;
    }

    //게시글 전체 조회
    public List<Board> findByAll() {

        log.info("게시글 전체조회 시작");

        //JPQL로
        String jpql = "SELECT b FROM Board b ORDER BY b.id DESC";
        TypedQuery query = em.createQuery(jpql, Board.class);
        List<Board> boardList = query.getResultList();
        return boardList;
    }
}//
