package jjalsel.be_playground.persistence.quiz;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

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

    @Column(nullable = false)
    @Comment("문제")
    private String questionTitle;

    @Column(nullable = false)
    @Comment("문제내용")
    private String questionContent;

    @Column(nullable = false)
    @Comment("정답")
    private String answer;

    @Column(nullable = false)
    @Comment("문제타입")
    private String type;

    @Column(nullable = true)
    @Comment("힌트")
    private String hint;

    @Column(nullable = false)
    @Comment("해설")
    private String explanation;

    @Column(nullable = false)
    @Comment("분야")
    private String field;

    @Column(nullable = false)
    @Comment("언어")
    private String lang;

    @Column(nullable = false)
    @Comment("문제 풀이 소요시간")
    private int time;
}
