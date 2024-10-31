package jjalsel.be_playground.persistence.quiz.custom;

import jjalsel.be_playground.api.quiz.dto.request.QuizItemRequest;
import jjalsel.be_playground.api.quiz.dto.request.QuizListRequest;
import jjalsel.be_playground.api.quiz.dto.request.QuizRequest;
import jjalsel.be_playground.persistence.quiz.QuizEntity;
import java.util.List;

public interface QuizRepositoryCustom {
    List<QuizEntity> findFilteredAndRandom(QuizListRequest quizListRequest);

    // 추가: 필터 조건에 맞는 퀴즈들의 time 합계를 반환
    Integer sumOfTime(String field, String lang);


    QuizEntity findFilteredAndRandomOne(QuizItemRequest quizItemRequest);

}
