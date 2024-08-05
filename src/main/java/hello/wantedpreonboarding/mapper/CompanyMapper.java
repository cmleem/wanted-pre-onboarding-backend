package hello.wantedpreonboarding.mapper;

import hello.wantedpreonboarding.dto.CompanyDto;
import hello.wantedpreonboarding.dto.response.CompanyResponseDto;
import hello.wantedpreonboarding.entity.Company;

public class CompanyMapper {
    public static CompanyDto toDto(Company entity) {
        if (entity == null) return null;
        return CompanyDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .tenure(entity.getTenure())
                .region(entity.getRegion())
                .industry(entity.getIndustry())
                .postingList(entity.getPostingList().stream().map(posting -> PostingMapper.toDto(posting, false)).toList())
                .build();
    }

    public static CompanyResponseDto toResponse(CompanyDto dto) {
        if (dto == null) return null;
        return CompanyResponseDto.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .tenure(dto.getTenure())
                .region(dto.getRegion())
                .industry(dto.getIndustry())
                .postingList(dto.getPostingList().stream().map(PostingMapper::toResponse).toList())
                .build();
    }
}
