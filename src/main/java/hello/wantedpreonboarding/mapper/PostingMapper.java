package hello.wantedpreonboarding.mapper;

import hello.wantedpreonboarding.dto.PostingDto;
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
                .deadLine(entity.getDeadLine())
                .build();
    }
}
