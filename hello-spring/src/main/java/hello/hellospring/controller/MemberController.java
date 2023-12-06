package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    // 이렇게 쓰게 되면 다른 컨테이너들이 memberService를 사용할 때 여러 개의 서비스를 만들게 된다.
    // 근데 사실 memberService는 하나만 만들면 된다. 따라서 의존성을 주입해서 같은 memberService를 주입받아서 사용한다.
    private final MemberService memberService;

    // @Autowired : 스프링 컨테이너에서 서비스를 가져와 사용할 수 있게 한다.
    // 근데 컨트롤러에서만 연결시켰다고 서비스에서도 컨테이너에 연결된게 아니기때문에 서비스에서도 연결시켜주어야 한다.
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
