package jjalsel.be_playground.persistence.quiz.custom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jjalsel.be_playground.api.quiz.dto.request.QuizItemRequest;
import jjalsel.be_playground.api.quiz.dto.request.QuizListRequest;
import jjalsel.be_playground.api.quiz.dto.request.QuizRequest;
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
    public List<QuizEntity> findFilteredAndRandom(QuizListRequest quizListRequest) {



        var query = queryFactory
                .selectFrom(quizEntity)
                .where(
                        fieldEq(quizListRequest.getField()),
                        langEq(quizListRequest.getLang())
                )
                .orderBy(com.querydsl.core.types.dsl.Expressions.numberTemplate(Double.class, "function('random')").asc());

        // count 값이 null이 아니고, 0보다 큰 경우에만 limit을 설정합니다.
        if (quizListRequest.getCount() != null && quizListRequest.getCount() > 0) {
            query = query.limit(quizListRequest.getCount());
        }

        return query.fetch();
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

    @Override
    public QuizEntity findFilteredAndRandomOne(QuizItemRequest quizItemRequest) {
        return queryFactory
                .selectFrom(quizEntity)
                .where(
                        fieldEq(quizItemRequest.getField()),
                        langEq(quizItemRequest.getLang()),
                        excludeQuizIdNotIn(quizItemRequest.getExcludeQuizId())
                )
                .orderBy(com.querydsl.core.types.dsl.Expressions.numberTemplate(Double.class, "function('random')").asc())
                .limit(1)
                .fetchOne();

    }


    private com.querydsl.core.types.dsl.BooleanExpression fieldEq(String field) {
        return field != null ? quizEntity.field.eq(field) : null;
    }

    private com.querydsl.core.types.dsl.BooleanExpression langEq(String lang) {
        return lang != null ? quizEntity.lang.eq(lang) : null;
    }

    private BooleanExpression excludeQuizIdNotIn(List<Long> excludeQuizId) {
        if (excludeQuizId == null || excludeQuizId.isEmpty()) {
            return null;
        }
        return quizEntity.id.notIn(excludeQuizId);
    }
}
