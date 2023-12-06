package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import java.util.List;
import java.util.Optional;

public class MemberService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();

    /**
     * 회원 가입
     */
    public Long join(Member member) {
        // 같은 이름이 있는 중복 회원 X
        validateDuplicateMember(member); // 중복 회원 검증

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    // ifPresent() : Optional일 때 사용 가능하며, null이 아니라 이미 값이 있으면 안에 있는 코드가 동작한다.
                    // 그냥 꺼내고 싶으면 get()을 사용하면 된다. (옵셔널일 경우에 보통 .get()을 사용해서 바로 꺼내는 것을 권장하지는 않는다.)
                    // 실무에서는 orElseGet()을 많이 사용하는데. 이 함수는 값이 없으면 orElseGet() 안에 있는 메서드를 실행해... 뭐 이런식으로 옵셔널 바인딩을 한다.
                    throw new IllegalArgumentException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
