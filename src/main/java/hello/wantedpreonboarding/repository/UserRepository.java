package hello.wantedpreonboarding.repository;

import hello.wantedpreonboarding.entity.User;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepositoryImplementation<User, Integer> {
}
