package hello.wantedpreonboarding.mapper;

import hello.wantedpreonboarding.dto.PostingDto;
import hello.wantedpreonboarding.dto.response.PostingResponseDto;
import hello.wantedpreonboarding.entity.Posting;

public class PostingMapper {
    public static PostingDto toDto(Posting entity) {
        return PostingDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .stack(entity.getStack())
                .region(entity.getRegion())
                .position(entity.getPosition())
                .incentive(entity.getIncentive())
                .company(CompanyMapper.toDto(entity.getCompany()))
                .deadline(entity.getDeadLine())
                .build();
    }

    public static PostingResponseDto toResponse(PostingDto posting) {
        return PostingResponseDto.builder()
                .id(posting.getId())
                .title(posting.getTitle())
                .content(posting.getContent())
                .stack(posting.getStack())
                .region(posting.getRegion())
                .position(posting.getPosition())
                .incentive(posting.getIncentive())
                .company(CompanyMapper.toResponse(posting.getCompany()))
                .build();
    }
}
