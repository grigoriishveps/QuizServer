package engine;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryQuiz extends PagingAndSortingRepository<Quiz, Long> {
    boolean existsById(Long id);
}
