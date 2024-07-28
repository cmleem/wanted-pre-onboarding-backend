package hello.wantedpreonboarding.repository;

import hello.wantedpreonboarding.entity.User;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepositoryImplementation<User, Integer> {
    Optional<User> findByUsername(String username);
}
