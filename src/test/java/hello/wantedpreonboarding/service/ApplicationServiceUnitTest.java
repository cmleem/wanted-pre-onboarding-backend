package hello.wantedpreonboarding.service;

import hello.wantedpreonboarding.dto.ApplicationDto;
import hello.wantedpreonboarding.entity.Application;
import hello.wantedpreonboarding.entity.Company;
import hello.wantedpreonboarding.entity.Posting;
import hello.wantedpreonboarding.entity.User;
import hello.wantedpreonboarding.entity.enums.CareerType;
import hello.wantedpreonboarding.entity.enums.PositionType;
import hello.wantedpreonboarding.entity.enums.RegionType;
import hello.wantedpreonboarding.repository.ApplicationRepository;
import hello.wantedpreonboarding.repository.PostingRepository;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceUnitTest {
    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PostingRepository postingRepository;
    @InjectMocks
    private ApplicationService applicationService;

    private Application mockedApplication;
    private User mockedUser;
    private Posting mockedPosting;
    private Company mockedCompany;

    private User buildUser() {
        return User.builder()
                .id(1)
                .username("testUser")
                .email("testUser@test.com")
                .position(PositionType.DEVELOPMENT)
                .career(CareerType.EntryLevel)
                .exposed(true)
                .build();
    }

    private Company buildCompany(int prefix) {
        return Company.builder()
                .id(prefix)
                .name("testCompany")
                .description("testCompanyDescription")
                .tenure(10)
                .industry("Engineering")
                .region(RegionType.ULSAN)
                .build();
    }

    private Posting buildPosting(int prefix) {
        return Posting.builder()
                .id(prefix)
                .title(prefix + "testPosting")
                .stack(prefix + "Java")
                .content("testContent")
                .company(mockedCompany)
                .deadLine(LocalDateTime.of(2024, 1, 1, 1, 1, 1))
                .incentive(10000)
                .position(PositionType.DEVELOPMENT)
                .region(RegionType.ULSAN)
                .build();
    }

    private Application buildApplication(int prefix) {
        return Application.builder()
                .id(prefix)
                .user(mockedUser)
                .posting(mockedPosting)
                .createdAt(LocalDateTime.of(2024, 1, 1, 1, 1, 1))
                .build();
    }

    @BeforeEach
    void setup() {
        mockedUser = buildUser();
        mockedCompany = buildCompany(0);
        mockedPosting = buildPosting(0);
        mockedApplication = buildApplication(0);
    }

    @Nested
    class Create {
        @Test
        void success() {
            // given
            doReturn(Optional.of(mockedUser)).when(userRepository).findByUsername(mockedUser.getUsername());
            doReturn(Optional.of(mockedPosting)).when(postingRepository).findById(mockedPosting.getId());
            doReturn(Optional.empty()).when(applicationRepository).findByUser(mockedUser);
            doReturn(mockedApplication).when(applicationRepository).save(any(Application.class));
            // when
            ApplicationDto dto = applicationService.createApplication(mockedUser.getUsername(), mockedPosting.getId());
            // then
            verify(applicationRepository).save(any(Application.class));
            assertThat(dto.getUser().getUsername()).isEqualTo(mockedUser.getUsername());
            assertThat(dto.getPosting().getTitle()).isEqualTo(mockedPosting.getTitle());
        }

        @Test
        void failNotFoundUser() {
            // given
            doReturn(Optional.empty()).when(userRepository).findByUsername(any(String.class));
            // when
            var thrown = assertThrows(IllegalArgumentException.class, () -> applicationService.createApplication(mockedUser.getUsername(), mockedPosting.getId()));
            // then
            assertThat(thrown.getMessage()).isEqualTo("User with name " + mockedUser.getUsername() + " not found");
            assertThat(thrown.getClass()).isEqualTo(IllegalArgumentException.class);
        }

        @Test
        void failNotFoundPosting() {
            // given
            doReturn(Optional.of(mockedUser)).when(userRepository).findByUsername(mockedUser.getUsername());
            doReturn(Optional.empty()).when(postingRepository).findById(any(Integer.class));
            // when
            var thrown = assertThrows(IllegalArgumentException.class, () -> applicationService.createApplication(mockedUser.getUsername(), mockedPosting.getId()));
            // then
            assertThat(thrown.getMessage()).isEqualTo("Posting with id " + mockedPosting.getId() + " not found");
            assertThat(thrown.getClass()).isEqualTo(IllegalArgumentException.class);
        }

        @Test
        void failAlreadyApplicationExists() {
            // given
            doReturn(Optional.of(mockedUser)).when(userRepository).findByUsername(mockedUser.getUsername());
            doReturn(Optional.of(mockedPosting)).when(postingRepository).findById(mockedPosting.getId());
            doReturn(Optional.of(mockedUser)).when(applicationRepository).findByUser(mockedUser);
            // when
            var thrown = assertThrows(IllegalArgumentException.class, () -> applicationService.createApplication(mockedUser.getUsername(), mockedPosting.getId()));
            // then
            assertThat(thrown.getMessage()).isEqualTo("Application with User id " + mockedUser.getId() + " already exists");
            assertThat(thrown.getClass()).isEqualTo(IllegalArgumentException.class);
        }
    }

    @Nested
    class Read {
        @Test
        void success() {
            // given
            doReturn(Optional.of(mockedApplication)).when(applicationRepository).findById(mockedApplication.getId());
            // when
            ApplicationDto dto = applicationService.readApplicationById(mockedApplication.getId());
            // then
            assertThat(dto).isNotNull();
            assertThat(dto.getUser().getUsername()).isEqualTo(mockedUser.getUsername());
            assertThat(dto.getPosting().getTitle()).isEqualTo(mockedPosting.getTitle());
        }

        @Test
        void failNotFoundApplication() {
            // given
            doReturn(Optional.empty()).when(applicationRepository).findById(any(Integer.class));
            // when
            var thrown = assertThrows(IllegalArgumentException.class, () -> applicationService.readApplicationById(mockedApplication.getId()));
            // then
            assertThat(thrown.getMessage()).isEqualTo("Application with id " + mockedApplication.getId() + " not found");
            assertThat(thrown.getClass()).isEqualTo(IllegalArgumentException.class);
        }
    }

    @Nested
    class ReadList {
        @Test
        void success() {
            // given
            List<Company> companyList = List.of(buildCompany(1), buildCompany(2), buildCompany(3));
            List<Posting> postingList = new ArrayList<>();
            for (int i = 1; i <= 3; i++) {
                postingList.add(
                        Posting.builder()
                                .company(companyList.get(i - 1))
                                .build()
                );
            }
            List<Application> applicationList = new ArrayList<>();
            for (int i = 1; i <= 3; i++) {
                applicationList.add(
                        Application.builder()
                                .id(i)
                                .posting(postingList.get(i - 1))
                                .build()
                );
            }

            PageRequest pageRequest = PageRequest.of(0, 3, Sort.Direction.ASC, "id");
            var page = new PageImpl<>(applicationList);

            doReturn(page).when(applicationRepository).findAll(pageRequest);

            // when
            Page<ApplicationDto> applications = applicationService.readApplicationList(pageRequest);

            // then
            verify(applicationRepository).findAll(pageRequest);
            assertThat(applications).isNotNull();
            assertThat(applications.getContent()).isNotNull();
            assertThat(applications.getContent().size()).isEqualTo(3);
        }
    }

    @Nested
    class Delete {
        @Test
        void success() {
            // given
            doReturn(Optional.of(mockedApplication)).when(applicationRepository).findById(mockedApplication.getId());
            // when
            applicationService.deleteApplication(mockedApplication.getId());
            // then
            verify(applicationRepository).delete(any(Application.class));
        }
    }
}
