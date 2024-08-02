package hello.wantedpreonboarding.repository;

import hello.wantedpreonboarding.entity.Application;
import hello.wantedpreonboarding.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    Optional<Application> findByUser(User user);
}
