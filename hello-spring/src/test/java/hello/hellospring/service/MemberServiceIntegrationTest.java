package hello.hellospring.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
/* @Transactional : 디비에 테스트를 모두 마친 후 insert에 대한 커밋을 하지않고 모두 롤백을 해주어서 @AfterEach없이 개별적으로 테스트가 가능하다.
                  쿼리는 정상적으로 날아가기 때문에 테스트는 가능하지만 실제 데이터가 커밋이 안되고 롤백되어 반영되지 않기 때문에 다음 테스트를 반복해서 실행 가능하다.
                  (테스트 클래스에 붙었을 때만 이런 기능으로 동작한다.)
                  보통은 서비스나 이런데에 붙으면 정상적으로 작동한다.

                  같이 알아두면 좋은 어노테이션 : @Commit

                  -----------------------------------------------------
                  스프링까지 같이 띄우는 테스트 -> 통합테스트
                  순수한 자바 코드만을 사용한 테스트 -> 단위테스트

                  일반적으로 단위 단위테스트로 쪼개서 테스트하는 것이 훨씬 좋다.
                  (스프링 컨테이너 없이 테스트 할 수 있어야 한다.)
 */
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
//    @Commit // @Transactional을 통해 롤백 하는 것을 무시(?)하고 DB에 적용(commit)해줄 수 있게 하는 어노테이션
    void 회원가입() {
        // given : 무언가 주어졌을 때
        Member member = new Member();
        member.setName("spring");

        // when : 언제?
        Long saveId = memberService.join(member);

        // then : 결과 값
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);

        // memberService.join() 메서드를 호출하면 IllegalStateException이 발생해야되는 코드
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

/*
        try {
            memberService.join(member2);
            fail();
        } catch(IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
*/

        // then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}