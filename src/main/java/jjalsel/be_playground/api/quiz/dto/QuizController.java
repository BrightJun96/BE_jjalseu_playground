package jjalsel.be_playground.api.quiz.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.base-path}/quiz")
@RequiredArgsConstructor
public class QuizController {

    @GetMapping("element")
    public String getQuizElement() {
        return "element";
    }
}
