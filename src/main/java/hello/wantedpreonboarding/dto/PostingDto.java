package hello.wantedpreonboarding.dto;

import hello.wantedpreonboarding.entity.enums.PositionType;
import hello.wantedpreonboarding.entity.enums.RegionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class PostingDto {
    private Integer id;
    private String title;
    private String content;
    private PositionType position;
    private Integer incentive;
    private LocalDateTime deadLine;
    private String stack;
    private RegionType region;
    private CompanyDto company;
}
