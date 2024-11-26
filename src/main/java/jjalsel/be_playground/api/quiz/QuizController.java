package jjalsel.be_playground.api.quiz;

import jakarta.validation.Valid;
import jjalsel.be_playground.api.quiz.dto.request.*;
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


    /**
     * 클라이언트단 API
     */
    // 퀴즈 단일 조회 ,사용자,관리자 둘다 사용
    @GetMapping("/item")
    public Response<?> getQuizElement(QuizItemRequest quizItemRequest) {
        return Response.ok(quizService.getQuizElement(quizItemRequest));
    }
    // 퀴즈 PK로 상세 조회
    @GetMapping("{quizId}")
    public Response<QuizResponse> getQuizById(@PathVariable Long quizId) {
        return Response.ok(quizService.getQuizById(quizId));
    }

    // 퀴즈 detailURL로 상세 조회
    @GetMapping("detail-url/{detailUrl}")
    public Response<QuizResponse> getQuizByDetailUrl(@PathVariable String detailUrl) {
        return Response.ok(quizService.getQuizByDetailUrl(detailUrl));
    }


    // 퀴즈목록 - Pk 목록만 반환
    @GetMapping("list-pk")
    public Response<List<Long>> getQuizPkList(QuizListRequest quizListRequest) {
        return Response.ok(quizService.getQuizPkList(quizListRequest));
    }
    // 퀴즈 detailURL 목록 반환
    @GetMapping("list-detail-url")
    public Response<List<String>> getQuizDetailUrlList(QuizListRequest quizListRequest) {
        return Response.ok(quizService.getQuizDetailUrlList(quizListRequest));
    }

    // 퀴즈 정답 확인
    @PostMapping("check")
    public Response<QuizCheckResponse> checkAnswer(@RequestBody @Valid QuizCheckRequest quizCheckRequest) {
        return Response.ok(quizService.checkAnswer(quizCheckRequest));
    }


    /**
     * 관리자단 API
     */
    // 퀴즈 목록
    @GetMapping("list")
    public Response<QuizResponseWithTotalTime> getQuizList(QuizListRequest quizListRequest) {
        return  Response.ok(quizService.getQuizList(quizListRequest));
    }




    // 퀴즈 수정
    @PatchMapping("{quizId}")
    public Response<?> partialUpdateQuiz(@PathVariable Long quizId, @RequestBody QuizUpdateRequest quizUpdateRequest) {
        quizService.partialUpdateQuiz(quizId, quizUpdateRequest);
        return Response.ok();
    }


    // 퀴즈 삭제
    @DeleteMapping("{quizId}")
    public Response<?> deleteQuiz(@PathVariable Long quizId) {
        quizService.deleteQuiz(quizId);
        return Response.ok();
    }

    // 퀴즈 등록
    @PostMapping("register")
    public Response<?> registerQuiz(@RequestBody @Valid QuizRequest quizRequest) {

        quizService.registerQuiz(quizRequest);
        return Response.ok();
    }







}
