package hello.wantedpreonboarding.repository;

import hello.wantedpreonboarding.entity.Posting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostingRepository extends JpaRepository<Posting, Integer> {

}
