package hello.wantedpreonboarding.repository;

import hello.wantedpreonboarding.entity.Company;
import hello.wantedpreonboarding.entity.enums.RegionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CompanyRepositoryUnitTest {
    @Autowired
    CompanyRepository companyRepository;

    private Company company;

    @BeforeEach
    void setup() {
        company = Company.builder()
                .name("company")
                .description("description")
                .region(RegionType.BUSAN)
                .tenure(10)
                .industry("industrial")
                .build();
    }

    @Nested
    class Read {
        @Test
        void success() {
            // given
            Company saved = companyRepository.save(company);
            // when
            Optional<Company> found = companyRepository.findById(saved.getId());
            // then
            assertThat(found).isPresent();
            assertThat(found.get().getName()).isEqualTo(company.getName());
            assertThat(found.get().getDescription()).isEqualTo(company.getDescription());
        }

        @Test
        void fail() {
            // given
            // when
            Optional<Company> found = companyRepository.findById(12345);
            // then
            assertThat(found).isNotPresent();
        }
    }
}
