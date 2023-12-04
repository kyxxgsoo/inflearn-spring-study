package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        // 여기서 data는 model에 담겨있는 key값이고, "hello!!"라는 값을 갖는다.
        model.addAttribute("data", "spring!!");
        // return hello는 templates에 있는 "hello"라는 화면을 찾는다.
        return "hello";
    }

}
