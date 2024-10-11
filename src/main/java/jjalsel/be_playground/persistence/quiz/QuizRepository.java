package jjalsel.be_playground.persistence.quiz;

import jjalsel.be_playground.persistence.quiz.custom.QuizRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<QuizEntity, Long>, QuizRepositoryCustom {
}
