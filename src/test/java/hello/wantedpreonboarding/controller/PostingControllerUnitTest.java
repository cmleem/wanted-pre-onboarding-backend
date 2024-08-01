package hello.wantedpreonboarding.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hello.wantedpreonboarding.dto.CompanyDto;
import hello.wantedpreonboarding.dto.PostingDto;
import hello.wantedpreonboarding.entity.enums.PositionType;
import hello.wantedpreonboarding.entity.enums.RegionType;
import hello.wantedpreonboarding.service.PostingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostingController.class)
public class PostingControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PostingService postingService;

    private PostingDto postingDto;
    private CompanyDto companyDto;

    @BeforeEach
    void setup() {
        companyDto = CompanyDto.builder()
                .id(1)
                .tenure(1)
                .industry("")
                .region(RegionType.BUSAN)
                .name("testCompany")
                .description("testCompany")
                .build();

        postingDto = PostingDto.builder()
                .id(1)
                .title("testTitle")
                .content("testContent")
                .position(PositionType.MANAGEMENT)
                .deadline(LocalDateTime.of(2024, 1, 1, 1, 1, 1))
                .incentive(0)
                .stack("Python")
                .region(RegionType.BUSAN)
                .company(companyDto)
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
            doReturn(postingDto).when(postingService).create(postingDto);

            // when
            ResultActions resultActions = mockMvc.perform(post("/api/posting/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(postingDto))
            );
            // then
            resultActions.andExpectAll(
                    status().isCreated(),
                    jsonPath("$.title").value(postingDto.getTitle()),
                    jsonPath("$.content").value(postingDto.getContent())
            ).andDo(print());
        }
    }
}
