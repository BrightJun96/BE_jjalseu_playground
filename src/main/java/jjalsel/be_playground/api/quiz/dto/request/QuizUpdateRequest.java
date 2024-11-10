package jjalsel.be_playground.api.quiz.dto.request;

import lombok.Builder;
import lombok.Getter;

// Patch 요청용 DTO
@Getter
@Builder
public class QuizUpdateRequest {

    private String title;
    private String content;
    private String subjectAnswer;
    private int[] multipleChoiceAnswer;
    private String type;
    private String hint;
    private String explanation;
    private String field;
    private String lang;
    private Integer time;
    private Integer level;
    private Boolean isMultiple;
    private String[] multipleChoiceContents;
}
