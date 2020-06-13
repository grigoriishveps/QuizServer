package engine;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface RepositoryCompletedQuiz extends PagingAndSortingRepository<CompletedQuiz, Long> {
    Page<CompletedQuiz> findByUserid(Long id, Pageable pageable);
}
