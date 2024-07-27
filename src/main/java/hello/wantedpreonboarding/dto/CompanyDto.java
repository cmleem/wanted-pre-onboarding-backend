package hello.wantedpreonboarding.dto;

import hello.wantedpreonboarding.entity.enums.RegionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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
}
