package hello.wantedpreonboarding.repository;

import hello.wantedpreonboarding.entity.User;
import hello.wantedpreonboarding.entity.enums.CareerType;
import hello.wantedpreonboarding.entity.enums.PositionType;
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
public class UserRepositoryUnitTest {
    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setup() {
        user = User.builder()
                .username("testUser")
                .email("test@test.com")
                .position(PositionType.DEVELOPMENT)
                .career(CareerType.EntryLevel)
                .build();
    }

    @Nested
    class Read {
        @Test
        void success() {
            // given
            User saved = userRepository.save(user);
            // when
            Optional<User> result = userRepository.findById(saved.getId());
            // then
            assertThat(result).isPresent();
            assertThat(result.get().getEmail()).isSameAs(saved.getEmail());
            assertThat(result.get().getUsername()).isEqualTo(saved.getUsername());
        }

        @Test
        void fail() {
            // given
            User saved = userRepository.save(user);
            // when
            Optional<User> result = userRepository.findById(123);
            // then
            assertThat(result).isNotPresent();
        }
    }

    @Nested
    class Delete {
        @Test
        void success() {
            // given
            User saved = userRepository.save(user);
            // when
            userRepository.delete(saved);
            // then
            assertThat(userRepository.findAll()).hasSize(0);
            assertThat(userRepository.findAll()).isEmpty();
        }
    }
}
