package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>(); // 실무에서는 동시성 문제때문에 공유되는 변수일때는 ConcurrentHashMap을 사용한다.
    private static long sequence = 0L; // 실무에서는 동시성 문제때문에 long이 아닌 AtomicLong을 사용한다.

    @Override
    public Member save(Member member) {
        member.setId(++sequence);   // member의 id값 셋팅
        store.put(member.getId(), member);  // id와 member 넣기
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); // Optinal.ofNullable()로 감싸서 반환하면 null이 반환될 수 있다. -> 이러면 클라이언트에서 해결 가능.
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
}
