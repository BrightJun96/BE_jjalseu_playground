package jjalsel.be_playground.api.quiz;

import jjalsel.be_playground.api.quiz.dto.request.QuizListRequest;
import jjalsel.be_playground.api.quiz.dto.request.QuizRequest;
import jjalsel.be_playground.api.quiz.dto.response.QuizResponse;
import jjalsel.be_playground.api.quiz.dto.response.QuizResponseWithTotalTime;
import jjalsel.be_playground.persistence.quiz.QuizEntity;
import jjalsel.be_playground.persistence.quiz.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;

    /*
      퀴즈 등록
     */
    public void registerQuiz(QuizRequest quizRequest) {

        quizRepository.save(quizRequest.toQuizEntity());
    }

   /*
       퀴즈 목록 (조건에 맞는 랜덤 10개)
     */
    public QuizResponseWithTotalTime getQuizList(QuizListRequest quizListRequest) {
        List<QuizEntity> quizzes = quizRepository.findFilteredAndRandom(
                quizListRequest.getField(),
                quizListRequest.getLang(),
                Math.min(quizListRequest.getCount(), 10)
        );

        // 각 퀴즈를 QuizResponse로 변환
        List<QuizResponse> quizResponses = quizzes.stream()
                .map(QuizResponse::fromEntity)
                .toList();

        // time 필드 합계 계산
        Integer totalTime = quizRepository.sumOfTime(
                quizListRequest.getField(),
                quizListRequest.getLang()
        );

        // 응답 객체에 퀴즈 목록과 총 합계 시간 포함
        return QuizResponseWithTotalTime.builder()
                .quizList(quizResponses)
                .totalTime(totalTime != null ? totalTime : 0)  // null 방지
                .build();
    }

}
