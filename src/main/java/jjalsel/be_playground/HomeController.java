package jjalsel.be_playground;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";  // templates 폴더 안의 index.html을 가리킴
    }
}
