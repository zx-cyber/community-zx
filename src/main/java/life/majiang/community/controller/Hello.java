package life.majiang.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Hello {
    @GetMapping("/")
    public String hello(){
        return "hello";
    }
}
