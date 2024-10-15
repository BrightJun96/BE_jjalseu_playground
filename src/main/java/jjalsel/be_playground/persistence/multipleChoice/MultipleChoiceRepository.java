package jjalsel.be_playground.persistence.multipleChoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MultipleChoiceRepository extends JpaRepository<MultipleChoiceEntity,Long> {
    Collection<Object> findByQuizId(Long id);
}
