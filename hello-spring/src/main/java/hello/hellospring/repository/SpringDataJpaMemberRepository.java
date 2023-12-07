package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    // 아래처럼 메서드 네임을 가지고 해결 가능하다. 아래 쿼리를 메서드 이름으로 자동으로 짜준다.
    // JPQL select m from Member m where m.name = ?
    @Override
    Optional<Member> findByName(String name);
}
