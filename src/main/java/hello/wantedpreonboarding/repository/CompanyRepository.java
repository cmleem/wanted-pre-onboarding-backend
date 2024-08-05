package hello.wantedpreonboarding.repository;

import hello.wantedpreonboarding.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
