package hello.wantedpreonboarding.repository;

import hello.wantedpreonboarding.entity.Posting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostingRepository extends JpaRepository<Posting, Integer> {
}
