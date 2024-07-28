package hello.wantedpreonboarding.service;

import hello.wantedpreonboarding.dto.UserDto;
import hello.wantedpreonboarding.entity.User;
import hello.wantedpreonboarding.entity.enums.CareerType;
import hello.wantedpreonboarding.entity.enums.PositionType;
import hello.wantedpreonboarding.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User mockedUser;

    private User buildUser(int prefix) {
        return User.builder()
                .username(prefix + "testUser")
                .email(prefix + "testUser@test.com")
                .position(PositionType.DEVELOPMENT)
                .career(CareerType.EntryLevel)
                .exposed(true)
                .build();
    }

    @BeforeEach
    void setup() {
        mockedUser = buildUser(0);
    }

    @Nested
    class Read {
        @Test
        void success() {
            // given
            doReturn(Optional.of(mockedUser)).when(userRepository).findByUsername(mockedUser.getUsername());
            // when
            UserDto dto = userService.getUserByUsername(mockedUser.getUsername());
            // then
            assertThat(dto).isNotNull();
            assertThat(dto.getUsername()).isEqualTo(mockedUser.getUsername());
            assertThat(dto.getEmail()).isEqualTo(mockedUser.getEmail());
        }

        @Test
        void failNotFoundUser() {
            // given
            doReturn(Optional.empty()).when(userRepository).findByUsername(mockedUser.getUsername());
            // when
            IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> userService.getUserByUsername(mockedUser.getUsername()));
            // then
            assertThat(thrown.getMessage()).isEqualTo("User with name " + mockedUser.getUsername() + " not found");
        }
    }

    @Nested
    class ReadList {
        @Test
        void success() {
            // given
            List<User> userList = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                userList.add(buildUser(i));
            }

            PageRequest pageRequest = PageRequest.of(0, 5, Sort.Direction.ASC, "id");
            PageImpl<User> page = new PageImpl<>(userList);

            doReturn(page).when(userRepository).findAll(pageRequest);
            // when
            Page<UserDto> users = userService.getUsers(pageRequest);
            // then
            verify(userRepository).findAll(pageRequest);
            assertThat(users).isNotNull();
            assertThat(users.getContent().size()).isEqualTo(5);
            assertThat(users.stream().toList().get(0).getUsername()).isEqualTo("1testUser");
        }

        @Test
        void failNoUsersFound() {
            // given
            PageRequest pageRequest = PageRequest.of(0, 5, Sort.Direction.ASC, "id");
            PageImpl<User> page = new PageImpl<>(List.of());

            doReturn(page).when(userRepository).findAll(pageRequest);
            // when
            Page<UserDto> users = userService.getUsers(pageRequest);
            // then
            verify(userRepository).findAll(pageRequest);
            assertThat(users).isEmpty();
        }
    }

    @Nested
    class Delete {
        @Test
        void success() {
            // given
            doReturn(Optional.of(mockedUser)).when(userRepository).findByUsername(mockedUser.getUsername());
            // when
            userService.deleteUserByUsername(mockedUser.getUsername());
            // then
            verify(userRepository).delete(mockedUser);
        }

        @Test
        void failNotFoundUser() {
            // given
            doReturn(Optional.empty()).when(userRepository).findByUsername(mockedUser.getUsername());
            // when
            IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> userService.deleteUserByUsername(mockedUser.getUsername()));
            // then
            assertThat(thrown.getMessage()).isEqualTo("User with name " + mockedUser.getUsername() + " not found");
        }
    }
}
