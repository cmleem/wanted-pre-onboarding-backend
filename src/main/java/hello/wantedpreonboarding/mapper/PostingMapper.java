package hello.wantedpreonboarding.mapper;

import hello.wantedpreonboarding.dto.PostingDto;
import hello.wantedpreonboarding.dto.response.PostingResponseDto;
import hello.wantedpreonboarding.entity.Posting;

public class PostingMapper {
    public static PostingDto toDto(Posting entity) {
        return toDto(entity, true);
    }

    public static PostingDto toDto(Posting entity, Boolean containCompany) {
        if (entity == null) return null;
        return PostingDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .stack(entity.getStack())
                .region(entity.getRegion())
                .position(entity.getPosition())
                .incentive(entity.getIncentive())
                .company(containCompany ? CompanyMapper.toDto(entity.getCompany()) : null)
                .deadline(entity.getDeadLine())
                .build();
    }

    public static PostingResponseDto toResponse(PostingDto dto) {
        if (dto == null) return null;
        PostingResponseDto response = PostingResponseDto.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .stack(dto.getStack())
                .region(dto.getRegion())
                .position(dto.getPosition())
                .incentive(dto.getIncentive())
                .deadline(dto.getDeadline())
                .build();
        if (dto.getCompany() != null) {
            response.setCompany(CompanyMapper.toResponse(dto.getCompany()));
        }
        return response;
    }
}
