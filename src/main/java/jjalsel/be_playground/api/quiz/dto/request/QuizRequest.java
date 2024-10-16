package jjalsel.be_playground.api.quiz.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jjalsel.be_playground.persistence.quiz.QuizEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuizRequest {

    @NotBlank(message = "문제 제목을 입력해주세요.")
    private String questionTitle;

    @NotBlank(message = "문제 내용을 입력해주세요.")
    private String questionContent;


    @NotBlank(message = "정답을 입력해주세요.")
    private String answer;


    @NotBlank(message = "문제 타입을 입력해주세요.")
    private String type;

    private String hint;

    @NotBlank(message = "해설을 입력해주세요.")
    private String explanation;


    @NotBlank(message = "분야를 입력해주세요.")
    private String field;


    @NotBlank(message = "언어를 입력해주세요.")
    private String lang;

    @Min(value = 1, message = "문제 풀이 소요시간은 1 이상이어야 합니다.")
    private int time;

    @Min(value = 1,message= "문제 난이도를 입력해주세요.")
    private int level;

    @NotNull(message = "객관식일 경우, 중복 선택 여부를 입력해주세요.")
    private boolean isMultiple;

    private String[] multipleChoiceContents;

    public QuizEntity toQuizEntity() {
        return QuizEntity.builder()
                .questionTitle(questionTitle)
                .questionContent(questionContent)
                .answer(answer)
                .type(type)
                .hint(hint)
                .explanation(explanation)
                .field(field)
                .lang(lang)
                .time(time)
                .level(level)
                .isMultiple(isMultiple)
                .build();
    }

    public QuizRequest toQuizRequest(){
        return QuizRequest.builder()
                .questionTitle(questionTitle)
                .questionContent(questionContent)
                .answer(answer)
                .type(type)
                .hint(hint)
                .explanation(explanation)
                .field(field)
                .lang(lang)
                .time(time)
                .level(level)
                .isMultiple(isMultiple)
                .multipleChoiceContents(multipleChoiceContents)
                .build();
    }
}
