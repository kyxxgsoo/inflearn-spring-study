package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    // JPA를 스려면 EntityManager를 주입받아서 사용해야 한다. (스프링부트가 자동으로 EntityManager라는것을 생성해주고, DB와 통신 등..귀찮은 것들 다 알아서 처리해줌)
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        // 영속
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // em.find(조회할 타입, 식별자)
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        // JQPL에서는 콜론(:)을 통해 파라미터 이름과 위치를 지정
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        // JPQL : 객체를 대상으로 쿼리를 날리는 방법 (아래에서는 Member 엔티티를 대상으로 쿼리를 날림)
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
