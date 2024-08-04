package hello.wantedpreonboarding.repository;

import hello.wantedpreonboarding.entity.Company;
import hello.wantedpreonboarding.entity.Posting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostingRepository extends JpaRepository<Posting, Integer> {
    Page<Posting> findAll(Specification<Posting> spec, Pageable pageable);
}
