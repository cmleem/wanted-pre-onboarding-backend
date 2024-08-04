package hello.wantedpreonboarding.dto.response;

import hello.wantedpreonboarding.entity.enums.RegionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class CompanyResponseDto {
    private Integer id;
    private String name;
    private String description;
    private RegionType region;
    private String industry;
    private Integer tenure;
    private List<PostingResponseDto> postingList;
}
