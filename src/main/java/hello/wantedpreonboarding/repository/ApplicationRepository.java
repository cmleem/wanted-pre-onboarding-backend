package hello.wantedpreonboarding.repository;

import hello.wantedpreonboarding.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
}
