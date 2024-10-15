package jjalsel.be_playground.persistence.quiz.custom;

import jjalsel.be_playground.api.quiz.dto.request.QuizListRequest;
import jjalsel.be_playground.persistence.quiz.QuizEntity;
import java.util.List;

public interface QuizRepositoryCustom {
    List<QuizEntity> findFilteredAndRandom(String field, String lang, int count);

    // 추가: 필터 조건에 맞는 퀴즈들의 time 합계를 반환
    Integer sumOfTime(String field, String lang);


}
