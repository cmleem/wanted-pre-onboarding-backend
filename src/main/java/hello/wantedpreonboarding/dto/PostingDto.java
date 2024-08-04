package hello.wantedpreonboarding.dto;

import hello.wantedpreonboarding.entity.enums.PositionType;
import hello.wantedpreonboarding.entity.enums.RegionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class PostingDto {
    private Integer id;
    private String title;
    private String content;
    private PositionType position;
    private Integer incentive;
    private LocalDateTime deadline;
    private String stack;
    private RegionType region;
    private CompanyDto company;
    @Builder.Default
    private List<PostingDto> postingList = new ArrayList<>();
}
