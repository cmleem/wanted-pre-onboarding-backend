package hello.wantedpreonboarding.dto;

import hello.wantedpreonboarding.entity.enums.RegionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class CompanyDto {
    private Integer id;
    private String name;
    private String description;
    private RegionType region;
    private String industry;
    private Integer tenure;
    private List<PostingDto> postingList;
}
