package hello.wantedpreonboarding.service;

import hello.wantedpreonboarding.dto.PostingDto;
import hello.wantedpreonboarding.entity.Company;
import hello.wantedpreonboarding.entity.Posting;
import hello.wantedpreonboarding.entity.enums.PositionType;
import hello.wantedpreonboarding.entity.enums.RegionType;
import hello.wantedpreonboarding.repository.CompanyRepository;
import hello.wantedpreonboarding.repository.PostingRepository;
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
public class PostingServiceUnitTest {
    @Mock
    private PostingRepository postingRepository;
    @Mock
    private CompanyRepository companyRepository;
    @InjectMocks
    private PostingService postingService;

    private Posting posting;
    private Company company;

    private Company buildCompany() {
        return Company.builder()
                .id(1)
                .name("company")
                .description("description")
                .region(RegionType.BUSAN)
                .tenure(10)
                .industry("industrial")
                .build();
    }

    private Posting buildPosting(int prefix) {
        return Posting.builder()
                .id(prefix + 1)
                .stack("Java")
                .region(RegionType.SEOUL)
                .company(company)
                .title(prefix + "title")
                .content(prefix + "details")
                .deadLine(LocalDateTime.of(2024, 1, 1, 1, 1, 1))
                .build();
    }

    @BeforeEach
    void setup() {
        company = buildCompany();
        posting = buildPosting(0);
    }

    @Nested
    class Create {
        @Test
        void success() {
            // given
            doReturn(Optional.of(company)).when(companyRepository).findByName(company.getName());
            doReturn(posting).when(postingRepository).save(any(Posting.class));
            // when
            PostingDto dto = postingService.create(posting.getTitle(), posting.getContent(), posting.getPosition(), posting.getIncentive(), posting.getDeadLine(), posting.getStack(), posting.getRegion(), company.getName());
            // then
            verify(postingRepository).save(any(Posting.class));
            assertThat(dto).isNotNull();
            assertThat(dto.getTitle()).isEqualTo(posting.getTitle());
            assertThat(dto.getContent()).isEqualTo(posting.getContent());
        }

        @Test
        void failNotFoundCompany() {
            // given
            doReturn(Optional.empty()).when(companyRepository).findByName(company.getName());
            // when
            IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> postingService.create(posting.getTitle(), posting.getContent(), posting.getPosition(), posting.getIncentive(), posting.getDeadLine(), posting.getStack(), posting.getRegion(), company.getName()));
            // then
            assertThat(thrown).isNotNull();
            assertThat(thrown.getMessage()).isEqualTo("Company with name " + company.getName() + " not found");
            assertThat(thrown.getClass()).isEqualTo(IllegalArgumentException.class);
        }
    }

    @Nested
    class Read {
        @Test
        void success() {
            // given
            doReturn(Optional.of(posting)).when(postingRepository).findById(posting.getId());
            // when
            PostingDto dto = postingService.readPosting(posting.getId());
            // then
            assertThat(dto).isNotNull();
            assertThat(dto.getTitle()).isEqualTo(posting.getTitle());
            assertThat(dto.getContent()).isEqualTo(posting.getContent());
        }

        @Test
        void failNotFoundPosting() {
            // given
            doReturn(Optional.empty()).when(postingRepository).findById(posting.getId());
            // when
            IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> postingService.readPosting(posting.getId()));
            // then
            assertThat(thrown).isNotNull();
            assertThat(thrown.getMessage()).isEqualTo("Posting with id " + posting.getId() + " not found");
            assertThat(thrown.getClass()).isEqualTo(IllegalArgumentException.class);
        }
    }

    @Nested
    class ReadList {
        @Test
        void success() {
            // given
            List<Posting> postingList = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                postingList.add(buildPosting(i));
            }

            PageRequest pageRequest = PageRequest.of(0, 5, Sort.Direction.ASC, "id");
            PageImpl<Posting> page = new PageImpl<>(postingList);

            doReturn(page).when(postingRepository).findAll(pageRequest);
            // when
            Page<PostingDto> dtos = postingService.readPostingList(pageRequest);
            // then
            verify(postingRepository).findAll(pageRequest);
            assertThat(dtos).isNotNull();
            assertThat(dtos).hasSize(5);
            assertThat(dtos.getContent().get(0).getTitle()).isEqualTo(postingList.get(0).getTitle());
        }
    }

    @Nested
    class Update {
        @Test
        void success() {
            // given
            doReturn(Optional.of(posting)).when(postingRepository).findById(posting.getId());
            Posting updated = Posting.builder()
                    .title("updated")
                    .content("updated")
                    .position(PositionType.MEDICAL)
                    .incentive(0)
                    .deadLine(LocalDateTime.of(2025, 1, 1, 1, 1, 1))
                    .stack("C++")
                    .region(RegionType.JEJU)
                    .build();
            // when
            PostingDto dto = postingService.updatePosting(posting.getId(), updated.getTitle(), updated.getContent(), updated.getPosition(), updated.getIncentive(), updated.getDeadLine(), updated.getStack(), updated.getRegion());
            // then
            assertThat(dto).isNotNull();
            assertThat(dto.getTitle()).isEqualTo(updated.getTitle());
            assertThat(dto.getContent()).isEqualTo(updated.getContent());
            assertThat(dto.getPosition()).isEqualTo(updated.getPosition());
            assertThat(dto.getIncentive()).isEqualTo(updated.getIncentive());
            assertThat(dto.getDeadLine()).isEqualTo(updated.getDeadLine());
            assertThat(dto.getStack()).isEqualTo(updated.getStack());
            assertThat(dto.getRegion()).isEqualTo(updated.getRegion());
        }
    }

    @Nested
    class Delete {
        @Test
        void success() {
            // given
            doReturn(Optional.of(posting)).when(postingRepository).findById(posting.getId());
            // when
            postingService.deletePosting(posting.getId());
            // then
            verify(postingRepository).delete(posting);
        }
    }
}
