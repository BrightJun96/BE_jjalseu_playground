package jjalsel.be_playground.persistence.quiz;


import jakarta.persistence.*;
import jjalsel.be_playground.common.utils.IntArrayConverter;
import jjalsel.be_playground.persistence.multipleChoice.MultipleChoiceEntity;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.List;

@Entity
@Getter
@Table(name = "quiz")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class QuizEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("PK")
    private Long id;

    @Column(nullable = false,columnDefinition = "TEXT")
    @Comment("문제")
    private String title;

    @Column(nullable = false,columnDefinition = "TEXT")
    @Comment("문제내용")
    private String content;

    @Column(nullable = false)
    @Comment("문제타입")
    private String type;

    @Column(nullable = true,columnDefinition = "TEXT")
    @Comment("힌트")
    private String hint;

    @Column(nullable = false,columnDefinition = "TEXT")
    @Comment("해설")
    private String explanation;

    @Column(nullable = false)
    @Comment("분야")
    private String field;

    @Column(nullable = false)
    @Comment("언어")
    private String lang;

    @Column(nullable = false)
    @Comment("문제 난이도")
    private int level;

    @Column(nullable = false)
    @Comment("객관식일 경우 ,중복 선택 여부")
    private boolean isMultiple;

    @Column(nullable = false)
    @Comment("문제 풀이 소요시간")
    private int time;

    @Column(nullable = true)
    @Comment("객관식 답안")
    @Convert(converter = IntArrayConverter.class)
    private int[] multipleChoiceAnswer;


    @Column(nullable = true,columnDefinition = "TEXT")
    @Comment("주관식 답안")
    private String subjectiveAnswer;


    // 객관식 선택지 리스트
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    @Comment("객관식 선택지 리스트")
    private List<MultipleChoiceEntity> multipleChoices;





}
