package hello.wantedpreonboarding.service;

import hello.wantedpreonboarding.dto.CompanyDto;
import hello.wantedpreonboarding.entity.Company;
import hello.wantedpreonboarding.entity.enums.RegionType;
import hello.wantedpreonboarding.repository.CompanyRepository;
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
public class CompanyServiceUnitTest {
    @Mock
    CompanyRepository companyRepository;

    @InjectMocks
    CompanyService companyService;

    private Company mockedCompany;

    private Company buildCompany(int prefix) {
        return Company.builder()
                .name(prefix + "testCompany")
                .description(prefix + "testCompanyDescription")
                .tenure(10)
                .industry("Engineering")
                .region(RegionType.ULSAN)
                .build();
    }

    @BeforeEach
    void setup() {
       mockedCompany = buildCompany(0);
    }

    @Nested
    class Read {
        @Test
        void success() {
            // given
            doReturn(Optional.of(mockedCompany)).when(companyRepository).findByName(mockedCompany.getName());
            // when
            CompanyDto dto = companyService.getCompanyByName(mockedCompany.getName());
            // then
            assertThat(dto.getName()).isEqualTo(mockedCompany.getName());
            assertThat(dto.getDescription()).isEqualTo(mockedCompany.getDescription());
            assertThat(dto.getIndustry()).isEqualTo(mockedCompany.getIndustry());
            assertThat(dto.getRegion()).isEqualTo(mockedCompany.getRegion());
        }

        @Test
        void failNotFoundCompany() {
            // given
            doReturn(Optional.empty()).when(companyRepository).findByName(mockedCompany.getName());
            // when
            IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> companyService.getCompanyByName(mockedCompany.getName()));
            // then
            assertThat(thrown.getMessage()).isEqualTo("Company with name " + mockedCompany.getName() + " not found");
        }
    }

    @Nested
    class ReadList {
        @Test
        void success() {
            // given
            List<Company> companyList = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                companyList.add(buildCompany(i));
            }

            PageRequest pageRequest = PageRequest.of(0, 3, Sort.Direction.ASC, "title");
            PageImpl<Company> page = new PageImpl<>(companyList);

            doReturn(page).when(companyRepository).findAll(pageRequest);
            // when
            Page<CompanyDto> companies = companyService.getCompanyList(pageRequest);
            // then
            verify(companyRepository).findAll(pageRequest);
            assertThat(companies).hasSize(5);
            assertThat(companies.getContent().get(0).getName()).isEqualTo("1testCompany");
        }
    }
}
