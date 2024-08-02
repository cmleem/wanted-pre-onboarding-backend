package hello.wantedpreonboarding.mapper;

import hello.wantedpreonboarding.dto.CompanyDto;
import hello.wantedpreonboarding.dto.response.CompanyResponseDto;
import hello.wantedpreonboarding.entity.Company;

public class CompanyMapper {
    public static CompanyDto toDto(Company entity) {
        return CompanyDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .tenure(entity.getTenure())
                .region(entity.getRegion())
                .industry(entity.getIndustry())
                .build();
    }

    public static CompanyResponseDto toResponse(CompanyDto company) {
        return CompanyResponseDto.builder()
                .id(company.getId())
                .name(company.getName())
                .description(company.getDescription())
                .tenure(company.getTenure())
                .region(company.getRegion())
                .industry(company.getIndustry())
                .build();
    }
}
