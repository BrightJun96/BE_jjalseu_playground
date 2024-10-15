package jjalsel.be_playground.persistence.multipleChoice;

import jakarta.persistence.*;
import jjalsel.be_playground.persistence.quiz.QuizEntity;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Table(name = "multiple_choice")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MultipleChoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("PK")
    private Long id;

    @Column(nullable = false)
    @Comment("문제")
    private String content;

    // QuizEntity와의 관계 설정 (ManyToOne)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    @Comment("연관된 퀴즈")
    private QuizEntity quiz;
}
