package hello.wantedpreonboarding.dto.request;

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
public class PostingUpdateRequestDto {
    private String title;
    private String content;
    private PositionType position;
    private Integer incentive;
    private LocalDateTime deadline;
    private String stack;
    private RegionType region;
}
