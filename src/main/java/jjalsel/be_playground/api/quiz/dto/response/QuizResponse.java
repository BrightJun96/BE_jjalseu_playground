package jjalsel.be_playground.api.quiz.dto.response;

import jjalsel.be_playground.persistence.quiz.QuizEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuizResponse {

    private Long quizId;

    private String questionTitle;

    private String questionContent;

    private String answer;

    private String type;

    private String hint;

    private String explanation;

    private String field;

    private String lang;

    private int time;

    // QuizEntity에서 QuizResponse로 변환하는 정적 메서드
    public static QuizResponse fromEntity(QuizEntity quizEntity) {
        return QuizResponse.builder()
                .quizId(quizEntity.getId())
                .questionTitle(quizEntity.getQuestionTitle())
                .questionContent(quizEntity.getQuestionContent())
                .answer(quizEntity.getAnswer())
                .type(quizEntity.getType())
                .hint(quizEntity.getHint())
                .explanation(quizEntity.getExplanation())
                .field(quizEntity.getField())
                .lang(quizEntity.getLang())
                .time(quizEntity.getTime())
                .build();
    }
}
