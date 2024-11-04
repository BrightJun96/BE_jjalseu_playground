package jjalsel.be_playground.api.quiz.dto.request;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class QuizItemRequest {

    // 분야
    private String field;

    // 프로그래밍 언어
    private String lang;

    // 제외 문제 pk
    private List<Long> excludeQuizId;

}
