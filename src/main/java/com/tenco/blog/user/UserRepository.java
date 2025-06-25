package com.tenco.blog.user;
import com.tenco.blog._core.errors.exception.Exception400;
import com.tenco.blog.board.Board;
import com.tenco.blog.board.BoardRepository;
import com.tenco.blog.board.BoardRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class UserRepository {

    private static final Logger log = LoggerFactory.getLogger(BoardRepository.class);
    private final EntityManager em;

    //회원 id 조회
    public User findById(Long id) {

        log.info("회원 id 조회");

        User user = em.find(User.class, id);
        if (user == null) {
            throw new Exception400("사용자를 찾을 수 없습니다");
        }
        return user;
    }


    //로그인 요청 (회원 이름/비밀번호 조회)
    public User findByUsernameAndPassword(String username, String password) {

        log.info("회원 이름/비밀번호 조회");

        try {
            String jpql = "SELECT u FROM User u WHERE u.username = :username AND u.password = :password ";
            TypedQuery<User> typedQuery = em.createQuery(jpql, User.class);
            typedQuery.setParameter("username", username);
            typedQuery.setParameter("password", password);
            return typedQuery.getSingleResult();
        } catch (Exception e) {
            return null; //필요하다면 직접 예외처리 설정
        }
    }

    //회원정보 저장 처리
    @Transactional
    public User save(User user) {

        log.info("회원 정보 저장 시작");

        em.persist(user);
        return user;
    }

    //회원명 중복체크 조회
    public User findByUsername(String username) {

        log.info("회원명 중복체크 조회");

        try {
            String jpql = "SELECT u FROM User u WHERE u.username = :username ";
            return em.createQuery(jpql, User.class)
                    .setParameter("username", username)
                    .getSingleResult();

        } catch (Exception e) {
            return null; //필요하다면 직접 예외처리 설정
        }
    }

    //회원 정보 수정
    @Transactional
    public User updateById(Long id, UserRequest.UpdateDTO reqDTO) {

        log.info("회원 정보 수정 시작 - id: {}", id);

        User user = findById(id);
        user.setPassword(reqDTO.getPassword());

        return user;
    }
}//class
