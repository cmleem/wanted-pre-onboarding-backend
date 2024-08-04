package hello.wantedpreonboarding.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import hello.wantedpreonboarding.entity.enums.PositionType;
import hello.wantedpreonboarding.entity.enums.RegionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class PostingResponseDto {
    private Integer id;
    private String title;
    private String content;
    private PositionType position;
    private Integer incentive;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime deadline;
    private String stack;
    private RegionType region;
    private CompanyResponseDto company;
}
