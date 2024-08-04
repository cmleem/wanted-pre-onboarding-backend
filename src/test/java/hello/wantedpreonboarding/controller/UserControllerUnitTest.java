package hello.wantedpreonboarding.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hello.wantedpreonboarding.dto.ApplicationDto;
import hello.wantedpreonboarding.dto.CompanyDto;
import hello.wantedpreonboarding.dto.PostingDto;
import hello.wantedpreonboarding.dto.UserDto;
import hello.wantedpreonboarding.entity.enums.CareerType;
import hello.wantedpreonboarding.entity.enums.PositionType;
import hello.wantedpreonboarding.entity.enums.RegionType;
import hello.wantedpreonboarding.service.ApplicationService;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ApplicationService applicationService;

    private UserDto userDto;
    private ApplicationDto applicationDto;
    private PostingDto postingDto;
    private CompanyDto companyDto;

    private PostingDto buildPosting(int num) {
        return PostingDto.builder()
                .id(num)
                .title("title" + num)
                .company(companyDto)
                .content("content" + num)
                .stack("Java")
                .region(RegionType.JEJU)
                .deadline(LocalDateTime.of(2024,1,1,1,1,1))
                .incentive(0)
                .position(PositionType.MANAGEMENT)
                .build();
    }

    @BeforeEach
    void setup() {
        userDto = UserDto.builder()
                .id(1)
                .username("testUser")
                .email("test@test.com")
                .career(CareerType.OneToThreeYears)
                .position(PositionType.MEDICAL)
                .exposed(true)
                .build();

        companyDto = CompanyDto.builder()
                .id(1)
                .name("testCompany")
                .industry("it")
                .tenure(10)
                .region(RegionType.SEJONG)
                .name("testDescription")
                .postingList(new ArrayList<>())
                .build();

        postingDto = PostingDto.builder()
                .id(1)
                .title("testTitle")
                .company(companyDto)
                .content("testContent")
                .stack("Java")
                .region(RegionType.JEJU)
                .deadline(LocalDateTime.of(2024, 1, 1, 1, 1, 1))
                .incentive(0)
                .position(PositionType.MANAGEMENT)
                .build();

        applicationDto = ApplicationDto.builder()
                .id(1)
                .user(userDto)
                .posting(postingDto)
                .createdAt(LocalDateTime.of(2024, 1, 1, 1, 1, 1))
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Nested
    class Create {
        @Test
        void success() throws Exception {
            // given
            doReturn(applicationDto).when(applicationService).createApplication(userDto.getUsername(), postingDto.getId());
            // when
            ResultActions resultActions = mockMvc.perform(post("/api/user/apply")
                    .param("username", userDto.getUsername())
                    .param("postingId", String.valueOf(postingDto.getId()))
            );
            // then
            resultActions.andExpectAll(
                    status().isCreated()
            ).andDo(print());
        }
    }

    @Nested
    class ReadApplication {
        @Test
        void success() throws Exception {
            // given
            PostingDto posting = buildPosting(1);
            ApplicationDto dto = ApplicationDto.builder()
                    .id(1)
                    .posting(posting)
                    .user(userDto)
                    .createdAt(LocalDateTime.now())
                    .build();
            doReturn(dto).when(applicationService).readApplicationById(eq(1));

            // when
            ResultActions resultActions = mockMvc.perform(get("/api/user/application/1"));
            // then
            resultActions.andExpectAll(
                    status().isOk(),
                    jsonPath("$.user.username").value(userDto.getUsername())
            );
        }
    }

    @Nested
    class ReadMyApplications {
        @Test
        void success() throws Exception {
            // given
            List<PostingDto> postings = List.of(buildPosting(1), buildPosting(2), buildPosting(3));
            List<ApplicationDto> applications = new ArrayList<>();
            for (int i = 1; i <= 3; i++) {
                applications.add(
                        ApplicationDto.builder()
                                .id(i)
                                .posting(postings.get(i - 1))
                                .user(userDto)
                                .createdAt(LocalDateTime.now())
                                .build()
                );
            }
            PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("id").descending());
            PageImpl<ApplicationDto> page = new PageImpl<>(applications, pageRequest, applications.size());
            doReturn(page).when(applicationService).readApplicationList(pageRequest);

            // when
            ResultActions resultActions = mockMvc.perform(get("/api/user/applications"));

            // then
            resultActions.andDo(print());
            resultActions.andExpectAll(
                    status().isOk(),
                    jsonPath("$.content.size()").value(3)
            );
        }
    }

    @Nested
    class Delete {
        @Test
        void success() throws Exception {
            // given
            doNothing().when(applicationService).deleteApplication(postingDto.getId());
            // when
            ResultActions resultActions = mockMvc.perform(delete("/api/user/applications/1"));
            // then
            resultActions.andExpectAll(
                    status().isOk()
            );
        }
    }
}
