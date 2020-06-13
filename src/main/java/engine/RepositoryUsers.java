package engine;

import engine.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryUsers extends CrudRepository<User, Long> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
}
