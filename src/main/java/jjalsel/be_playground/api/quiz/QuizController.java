package jjalsel.be_playground.api.quiz;

import jakarta.validation.Valid;
import jjalsel.be_playground.api.quiz.dto.request.QuizCheckRequest;
import jjalsel.be_playground.api.quiz.dto.request.QuizItemRequest;
import jjalsel.be_playground.api.quiz.dto.request.QuizListRequest;
import jjalsel.be_playground.api.quiz.dto.request.QuizRequest;
import jjalsel.be_playground.api.quiz.dto.response.QuizCheckResponse;
import jjalsel.be_playground.api.quiz.dto.response.QuizResponse;
import jjalsel.be_playground.api.quiz.dto.response.QuizResponseWithTotalTime;
import jjalsel.be_playground.common.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.base-path}/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    // 퀴즈 단일 조회
    @GetMapping("/item")
    public Response<?> getQuizElement(QuizItemRequest quizItemRequest) {
        return Response.ok(quizService.getQuizElement(quizItemRequest));
    }

    // 퀴즈 등록
    @PostMapping("register")
    public Response<?> registerQuiz(@RequestBody @Valid QuizRequest quizRequest) {

        quizService.registerQuiz(quizRequest);
        return Response.ok();

    }

    // 퀴즈 목록
    @GetMapping("list")
    public Response<QuizResponseWithTotalTime> getQuizList(QuizListRequest quizListRequest) {
        return  Response.ok(quizService.getQuizList(quizListRequest));
    }

    // 퀴즈 정답 확인
    @PostMapping("check")
    public Response<QuizCheckResponse> checkAnswer(@RequestBody @Valid QuizCheckRequest quizCheckRequest) {
        return Response.ok(quizService.checkAnswer(quizCheckRequest));
    }



}
