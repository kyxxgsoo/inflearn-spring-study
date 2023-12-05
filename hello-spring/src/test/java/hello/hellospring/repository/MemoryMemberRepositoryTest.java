package hello.hellospring.repository;

import static org.assertj.core.api.Assertions.*;

import hello.hellospring.domain.Member;
//import org.junit.jupiter.api.Assertions;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class MemoryMemberRepositoryTest {
    /*
    TDD(테스트 주도 개발) : 테스트 클래스를 먼저 작성한 다음에 거기에 해당하는 코드를 역으로 구현하는 방법.
     */

    MemoryMemberRepository repository = new MemoryMemberRepository();

    // @AfterEach : 각 메서드들이 끝날 때마다 실행되는 call-back 메서드를 만드는 어노테이션.
    // 테스트할 때 TestClass단위로 테스트를 진행하게 되면 모든 함수가 호출되게 되는데, 이 때, 함수가 호출되는 순서를 보장할 수 없다.
    // 따라서 각 함수는 독립적으로 진행되야하는데 이게 보장이 안되기 때문에, @AfterEach함수로 각 메서드가 테스트되고 끝날 때마다
    // 데이터를 초기화시켜주어야 한다. (저장소를 각 테스트가 끝난 후 모두 지운다. -> 테스트끼리는 의존관계가 없어야되기 때문에 공용 데이터를 지우는 과정이 필요하다.)
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    // 이 테스트들을 빌드 툴이랑 엮어놓으면. 빌드할 때 오류 테스트에서 통과하지 않으면 다음 단계로 못넘어가게 막아버림.
    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();  // 보통 Optional을 .get() 이런식으로 가져오면 안된다 함...
//        System.out.println("result = " + (result == member));
//        Assertions.assertEquals(member, result);  // "org.junit.jupiter.api.Assertions"를 import 해야 사용 가능.
        assertThat(member).isEqualTo(result); // "org.assertj.core.api.Assertions"를 import 해야 사용 가능.
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result1 = repository.findByName("spring1").get();

        assertThat(result1).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member1);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
