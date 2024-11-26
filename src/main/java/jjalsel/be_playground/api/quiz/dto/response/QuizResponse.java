package jjalsel.be_playground.api.quiz.dto.response;

import jjalsel.be_playground.persistence.quiz.QuizEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class QuizResponse {

    // 퀴즈 ID
    private Long quizId;

    // 제목
    private String title;

    // 문제 내용
    private String content;

    // 객관식 답안
    private int[] multipleChoiceAnswer;

    // 주관식 답안
    private String subjectAnswer;

    // 레벨
    private int level;

    // 타입
    private String type;

    // 힌트
    private String hint;

    // 해설
    private String explanation;

    // 분야
    private String field;

    // 언어
    private String lang;

    // 소요시간
    private int time;

    // 객관식 답안 정보
    private List<MultipleChoiceResponse> multipleChoices;

    // 생성일
    private String createdAt;

    // 수정일
    private String updatedAt;

    // 메타데이터-제목
    private String metaTitle;

    // 메타데이터-설명
    private String metaDescription;

    // 메타데이터-이미지URL
    private String metaImageUrl;

    // 상세 URL
    private String detailUrl;


    // QuizEntity에서 QuizResponse로 변환하는 정적 메서드
    public static QuizResponse fromEntity(QuizEntity quizEntity, List<MultipleChoiceResponse> multipleChoices) {
        return QuizResponse.builder()
                .quizId(quizEntity.getId())
                .title(quizEntity.getTitle())
                .content(quizEntity.getContent())
                .multipleChoiceAnswer(quizEntity.getMultipleChoiceAnswer())
                .subjectAnswer(quizEntity.getSubjectiveAnswer())
                .type(quizEntity.getType())
                .hint(quizEntity.getHint())
                .explanation(quizEntity.getExplanation())
                .field(quizEntity.getField())
                .lang(quizEntity.getLang())
                .time(quizEntity.getTime())
                .multipleChoices(multipleChoices)
                .createdAt(quizEntity.getCreatedDate().toString())
                .updatedAt(quizEntity.getUpdatedDate().toString())
                .metaTitle(quizEntity.getMetaTitle())
                .metaDescription(quizEntity.getMetaDescription())
                .metaImageUrl(quizEntity.getMetaImageUrl())
                .detailUrl(quizEntity.getDetailUrl())
                .build();
    }
}
