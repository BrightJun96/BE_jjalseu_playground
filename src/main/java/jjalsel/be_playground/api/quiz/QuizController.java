package jjalsel.be_playground.api.quiz;

import jakarta.validation.Valid;
import jjalsel.be_playground.api.quiz.dto.request.QuizItemRequest;
import jjalsel.be_playground.api.quiz.dto.request.QuizListRequest;
import jjalsel.be_playground.api.quiz.dto.request.QuizRequest;
import jjalsel.be_playground.api.quiz.dto.response.QuizResponse;
import jjalsel.be_playground.api.quiz.dto.response.QuizResponseWithTotalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.base-path}/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping("element")
    public String getQuizElement() {
        return "element";
    }

    // 퀴즈 등록
    @PostMapping("register")
    public void registerQuiz(@RequestBody @Valid QuizRequest quizRequest) {

        quizService.registerQuiz(quizRequest);

    }

    // 퀴즈 목록
    @GetMapping("list")
    public QuizResponseWithTotalTime getQuizList(QuizListRequest quizListRequest) {
        return quizService.getQuizList(quizListRequest);
    }



}
