package hello.wantedpreonboarding.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import hello.wantedpreonboarding.entity.enums.PositionType;
import hello.wantedpreonboarding.entity.enums.RegionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostingCreateRequestDto {
    private String title;
    private String content;
    private PositionType position;
    private Integer incentive;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime deadline;
    private String stack;
    private RegionType region;
    private String companyName;
}
