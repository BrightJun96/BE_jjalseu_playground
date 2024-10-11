package jjalsel.be_playground.api.quiz.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class QuizResponseWithTotalTime {

    private List<QuizResponse> quizList;

    // 퀴즈의 총 풀이 시간 합계
    private int totalTime;
}
