package hello.wantedpreonboarding.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hello.wantedpreonboarding.dto.CompanyDto;
import hello.wantedpreonboarding.dto.PostingDto;
import hello.wantedpreonboarding.dto.request.PostingCreateRequestDto;
import hello.wantedpreonboarding.dto.request.PostingUpdateRequestDto;
import hello.wantedpreonboarding.entity.enums.PositionType;
import hello.wantedpreonboarding.entity.enums.RegionType;
import hello.wantedpreonboarding.service.PostingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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

    private PostingDto buildPosting(int num) {
        return PostingDto.builder()
                .id(num)
                .title("testTitle" + num)
                .content("testContent" + num)
                .position(PositionType.MANAGEMENT)
                .deadline(LocalDateTime.of(2024, 1, 1, 1, 1, 1))
                .incentive(0)
                .stack("Python")
                .region(RegionType.BUSAN)
                .company(companyDto)
                .build();
    }

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
            PostingCreateRequestDto request = PostingCreateRequestDto.builder()
                    .title("testTitle")
                    .content("testContent")
                    .position(PositionType.MANAGEMENT)
                    .deadline(LocalDateTime.of(2024, 1, 1, 1, 1, 1))
                    .incentive(0)
                    .stack("Python")
                    .region(RegionType.BUSAN)
                    .companyName("testCompany")
                    .build();
            doReturn(postingDto).when(postingService).create(any(PostingDto.class), any());

            // when
            ResultActions resultActions = mockMvc.perform(post("/api/posting/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            );
            // then
            resultActions.andExpectAll(
                    status().isCreated(),
                    jsonPath("$.title").value(postingDto.getTitle()),
                    jsonPath("$.content").value(postingDto.getContent())
            ).andDo(print());
        }
    }

    @Nested
    class Read {
        @Test
        void success() throws Exception {
            // given
            doReturn(postingDto).when(postingService).readPosting(postingDto.getId());
            // when
            ResultActions resultActions = mockMvc.perform(get("/api/posting/read/" + postingDto.getId()));
            // then
            resultActions.andExpectAll(
                    status().isOk(),
                    jsonPath("$.title").value(postingDto.getTitle()),
                    jsonPath("$.content").value(postingDto.getContent()),
                    jsonPath("$.position").value(postingDto.getPosition().toString())
            );
        }
    }

    @Nested
    class ReadList {

        @Test
        void success() throws Exception {
            // given
            List<PostingDto> list = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                list.add(buildPosting(i));
            }
            var pageRequest = PageRequest.of(0, 10, Sort.by("id").descending());
            var page = new PageImpl<>(list, pageRequest, 5);

            doReturn(page).when(postingService).readPostingList(pageRequest, "");
            // when
            ResultActions resultActions = mockMvc.perform(get("/api/posting/read/list"));
            // then
            resultActions.andExpectAll(
                    status().isOk(),
                    jsonPath("$.content").isArray(),
                    jsonPath("$.content.size()").value(5),
                    jsonPath("$.content[0].title").value("testTitle1")
            );
        }
    }

    @Nested
    class Update {
        @Test
        void success() throws Exception {
            // given
            PostingDto updated = buildPosting(2);
            PostingUpdateRequestDto request = PostingUpdateRequestDto.builder()
                    .title("testTitle")
                    .content("testContent")
                    .position(PositionType.MANAGEMENT)
                    .deadline(LocalDateTime.of(2024, 1, 1, 1, 1, 1))
                    .incentive(0)
                    .stack("Python")
                    .region(RegionType.BUSAN)
                    .build();

            doReturn(updated).when(postingService).updatePosting(eq(postingDto.getId()), any(PostingDto.class));
            // when
            ResultActions resultActions = mockMvc.perform(patch("/api/posting/update/" + postingDto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            );
            // then
            resultActions.andExpectAll(
                    status().isOk(),
                    jsonPath("$.title").value(updated.getTitle()),
                    jsonPath("$.content").value(updated.getContent())
            );
        }
    }

    @Nested
    class Delete {
        @Test
        void success() throws Exception {
            // given
            doNothing().when(postingService).deletePosting(postingDto.getId());
            // when
            ResultActions resultActions = mockMvc.perform(delete("/api/posting/delete/" + postingDto.getId()));
            // then
            verify(postingService).deletePosting(postingDto.getId());
            resultActions.andExpectAll(
                    status().isOk(),
                    jsonPath("$.title").doesNotExist()
            );
        }
    }
}
