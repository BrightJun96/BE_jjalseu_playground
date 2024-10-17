package jjalsel.be_playground.api.quiz.dto.response;

import jjalsel.be_playground.persistence.multipleChoice.MultipleChoiceEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MultipleChoiceResponse {

    private Long multipleChoiceId;

    private String content;

    private Long quizId;

    private int number;


    public static MultipleChoiceResponse fromEntity(MultipleChoiceEntity multipleChoiceEntity) {
        return MultipleChoiceResponse.builder()
                .multipleChoiceId(multipleChoiceEntity.getId())
                .content(multipleChoiceEntity.getContent())
                .quizId(multipleChoiceEntity.getQuiz().getId())
                .number(multipleChoiceEntity.getNumber())
                .build();
    }
}
