package jjalsel.be_playground.persistence.quiz.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jjalsel.be_playground.persistence.quiz.QuizEntity;
import jjalsel.be_playground.persistence.quiz.custom.QuizRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static jjalsel.be_playground.persistence.quiz.QQuizEntity.quizEntity;

@Repository
@RequiredArgsConstructor
public class QuizRepositoryImpl implements QuizRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<QuizEntity> findFilteredAndRandom(String field, String lang, int count) {


        return queryFactory
                .selectFrom(quizEntity)
                .where(
                        fieldEq(field),
                        langEq(lang)
                )
                .orderBy(com.querydsl.core.types.dsl.Expressions.numberTemplate(Double.class, "function('random')").asc())
                .limit(count)
                .fetch();
    }

    // 모든 데이터의 time 필드 합계를 계산하는 메서드 추가
    @Override
    public Integer sumOfTime(String field, String lang) {
        return queryFactory
                .select(quizEntity.time.sum())
                .from(quizEntity)
                .where(
                        fieldEq(field),
                        langEq(lang)
                )
                .fetchOne();
    }



    private com.querydsl.core.types.dsl.BooleanExpression fieldEq(String field) {
        return field != null ? quizEntity.field.eq(field) : null;
    }

    private com.querydsl.core.types.dsl.BooleanExpression langEq(String lang) {
        return lang != null ? quizEntity.lang.eq(lang) : null;
    }
}
