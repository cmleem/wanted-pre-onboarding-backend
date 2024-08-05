package hello.wantedpreonboarding.controller;

import hello.wantedpreonboarding.dto.CompanyDto;
import hello.wantedpreonboarding.entity.enums.RegionType;
import hello.wantedpreonboarding.service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CompanyController.class)
public class CompanyControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CompanyService companyService;

    private CompanyDto company;

    private CompanyDto buildCompany(int num) {
        return CompanyDto.builder()
                .id(num)
                .description("testCompany" + num)
                .tenure(1)
                .industry("it")
                .region(RegionType.BUSAN)
                .name("testCompany")
                .postingList(new ArrayList<>())
                .build();
    }

    @BeforeEach
    void setup() {
        company = buildCompany(1);
    }

    @Nested
    class GetCompany {
        @Test
        void success() throws Exception {
            // given
            doReturn(company).when(companyService).findCompany(company.getId());
            // when
            ResultActions resultActions = mockMvc.perform(get("/api/company/" + company.getId()));
            // then
            resultActions.andExpectAll(
                    status().isOk(),
                    jsonPath("$.name").value(company.getName()),
                    jsonPath("$.industry").value(company.getIndustry()),
                    jsonPath("$.description").value(company.getDescription()),
                    jsonPath("$.region").value(company.getRegion().toString())
            );
        }
    }

    @Nested
    class GetCompanyList {
        @Test
        void success() throws Exception {
            // given
            List<CompanyDto> list = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                list.add(buildCompany(i + 1));
            }
            var pageRequest = PageRequest.of(0, 10, Sort.by("id").descending());
            var page = new PageImpl<>(list, pageRequest, list.size());
            doReturn(page).when(companyService).findCompanyList(pageRequest);
            // when
            ResultActions resultActions = mockMvc.perform(get("/api/company/"));
            // then
            resultActions.andExpectAll(
                    status().isOk(),
                    jsonPath("$.content").isArray(),
                    jsonPath("$.content.size()").value(5),
                    jsonPath("$.content[0].name").value(list.get(0).getName())
            );
        }
    }
}
