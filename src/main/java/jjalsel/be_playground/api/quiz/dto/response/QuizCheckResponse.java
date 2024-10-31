package jjalsel.be_playground.api.quiz.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class QuizCheckResponse {

        // 정답 여부
        private boolean isCorrect;

        // 정답
        private int[] answer;

        // 사용자가 입력한 답안
        private List<Integer> userAnswer;
}
