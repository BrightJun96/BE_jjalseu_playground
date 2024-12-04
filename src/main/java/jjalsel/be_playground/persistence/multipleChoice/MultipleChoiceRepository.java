package jjalsel.be_playground.persistence.multipleChoice;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MultipleChoiceRepository extends JpaRepository<MultipleChoiceEntity,Long> {
    Collection<Object> findByQuizId(Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM MultipleChoiceEntity m WHERE m.quiz.id = :quizId")
    void deleteByQuizId(Long quizId);
}
