package hello.wantedpreonboarding.repository;

import hello.wantedpreonboarding.entity.Application;
import hello.wantedpreonboarding.entity.Company;
import hello.wantedpreonboarding.entity.Posting;
import hello.wantedpreonboarding.entity.User;
import hello.wantedpreonboarding.entity.enums.CareerType;
import hello.wantedpreonboarding.entity.enums.PositionType;
import hello.wantedpreonboarding.entity.enums.RegionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostingRepositoryUnitTest {
    @Autowired
    PostingRepository postingRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    UserRepository userRepository;

    private Posting posting;
    private Company company;

    private Company buildCompany() {
        return Company.builder()
                .name("company")
                .description("description")
                .region(RegionType.BUSAN)
                .tenure(10)
                .industry("industrial")
                .build();
    }

    private Posting buildPosting(int postfix) {
        return Posting.builder()
                .stack("Java")
                .region(RegionType.SEOUL)
                .company(company)
                .title("title" + postfix)
                .content("details" + postfix)
                .deadLine(LocalDateTime.of(2024, 1, 1, 1, 1, 1))
                .build();
    }

    @BeforeEach
    void setup() {
        company = buildCompany();
        companyRepository.save(company);
        posting = buildPosting(0);
    }

    @Nested
    class Create {
        @Test
        void success() {
            // given
            // when
            Posting saved = postingRepository.save(posting);
            // then
            assertThat(saved).isNotNull();
            assertThat(saved).isEqualTo(posting);
        }
    }

    @Nested
    class Read {
        @Test
        void success() {
            // given
            Posting saved = postingRepository.save(posting);
            // when
            Optional<Posting> found = postingRepository.findById(saved.getId());
            // then
            assertThat(found).isPresent();
            assertThat(found.get().getCompany()).isEqualTo(saved.getCompany());
            assertThat(found.get().getTitle()).isEqualTo(saved.getTitle());
            assertThat(found.get().getStack()).isEqualTo(saved.getStack());
        }

        @Test
        void fail() {
            // given
            // when
            Optional<Posting> found = postingRepository.findById(12345);
            // then
            assertThat(found).isNotPresent();
        }
    }

    @Nested
    class Update {
        @Test
        void success() {
            // given
            Posting saved = postingRepository.save(posting);
            // when
            saved.updateTitle("updatedTitle");
            saved.updateStack("Python");
            saved.updateDeadLine(LocalDateTime.of(2024, 12, 31, 1, 1, 1));
            saved.updateContent("updatedContent");
            saved.updateRegion(RegionType.ULSAN);
            saved.updateIncentive(500000);
            saved.updatePosition(PositionType.MANAGEMENT);
            // then
            Posting found = postingRepository.findById(saved.getId()).get();

            assertThat(found).isNotNull();
            assertThat(found.getTitle()).isEqualTo("updatedTitle");
            assertThat(found.getStack()).isEqualTo("Python");
            assertThat(found.getDeadLine()).isEqualTo(LocalDateTime.of(2024, 12, 31, 1, 1, 1));
            assertThat(found.getContent()).isEqualTo("updatedContent");
            assertThat(found.getRegion()).isEqualTo(RegionType.ULSAN);
            assertThat(found.getIncentive()).isEqualTo(500000);
            assertThat(found.getPosition()).isEqualTo(PositionType.MANAGEMENT);
        }
    }

    @Nested
    class Delete {
        @Test
        void success() {
            // given
            Posting saved = postingRepository.save(posting);
            // when
            postingRepository.delete(saved);
            // then
            List<Posting> all = postingRepository.findAll();
            assertThat(all).isNotNull();
            assertThat(all).isEmpty();
        }
    }

    @Nested
    class Page {
        @Test
        void success() {
            // given
            for (int i = 1; i <= 5; i++) {
                postingRepository.save(buildPosting(i));
            }
            PageRequest pageRequest = PageRequest.of(0, 5, Sort.Direction.ASC, "id");
            // when
            var page = postingRepository.findAll(pageRequest);
            // then
            assertThat(page).isNotNull();
            assertThat(page).hasSize(5);
        }
    }
}
