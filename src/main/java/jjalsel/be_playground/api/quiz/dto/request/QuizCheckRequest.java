package jjalsel.be_playground.api.quiz.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class QuizCheckRequest {

    // 퀴즈 ID
    private long quizId;
    // 정답
    private List<Integer> userAnswer;
}
