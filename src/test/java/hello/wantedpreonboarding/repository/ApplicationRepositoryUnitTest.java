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
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ApplicationRepositoryUnitTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    PostingRepository postingRepository;

    private Application application ;
    private User user;
    private Posting posting;

    private User buildUser() {
        return User.builder()
                .username("testUser")
                .email("test@test.com")
                .position(PositionType.DEVELOPMENT)
                .career(CareerType.EntryLevel)
                .build();
    }

    private Company buildCompany() {
        return Company.builder()
                .name("company")
                .description("description")
                .region(RegionType.BUSAN)
                .tenure(10)
                .industry("industrial")
                .build();
    }

    private Posting buildPosting() {
        Company company = buildCompany();
        companyRepository.save(company);

        return Posting.builder()
                .stack("Java")
                .region(RegionType.SEOUL)
                .company(company)
                .title("title")
                .content("details")
                .deadLine(LocalDateTime.of(2024, 12, 12, 1, 1, 1))
                .build();
    }

    private Application buildApplication() {
        return Application.builder()
                .user(user)
                .posting(posting)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @BeforeEach
    void setup() {
        user = buildUser();
        userRepository.save(user);
        posting = buildPosting();
        postingRepository.save(posting);
        application = buildApplication();
    }

    @Nested
    class Create {
        @Test
        void success() {
            // given
            // when
            Application saved = applicationRepository.save(application);
            // then
            assertThat(saved).isNotNull();
            assertThat(saved.getUser()).isEqualTo(user);
            assertThat(saved.getPosting()).isEqualTo(posting);
        }
    }

    @Nested
    class Read {
        @Test
        void success() {
            // given
            Application saved = applicationRepository.save(application);
            // when
            Optional<Application> found = applicationRepository.findById(saved.getId());
            // then
            assertThat(found).isPresent();
            assertThat(found.get().getUser()).isEqualTo(user);
            assertThat(found.get().getPosting()).isEqualTo(posting);
        }

        @Test
        void fail() {
            // given
            // when
            Optional<Application> found = applicationRepository.findById(12345);
            // then
            assertThat(found).isNotPresent();
        }
    }

    @Nested
    class Delete {
        @Test
        void success() {
            // given
            Application saved = applicationRepository.save(application);
            // when
            applicationRepository.delete(saved);
            // then
            List<Application> all = applicationRepository.findAll();
            assertThat(all).isEmpty();
            assertThat(all).isNotNull();
        }
    }
}
