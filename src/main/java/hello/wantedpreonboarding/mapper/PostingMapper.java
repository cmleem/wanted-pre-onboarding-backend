package hello.wantedpreonboarding.mapper;

import hello.wantedpreonboarding.dto.PostingDto;
import hello.wantedpreonboarding.dto.response.PostingResponseDto;
import hello.wantedpreonboarding.entity.Posting;

import java.util.List;

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

    public static PostingResponseDto toResponse(PostingDto dto) {
        PostingResponseDto response = PostingResponseDto.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .stack(dto.getStack())
                .region(dto.getRegion())
                .position(dto.getPosition())
                .incentive(dto.getIncentive())
                .company(CompanyMapper.toResponse(dto.getCompany()))
                .build();
        if (!dto.getPostingList().isEmpty()) {
            List<PostingResponseDto> postingList = dto.getPostingList().stream()
                    .map(postingDto -> toResponse(postingDto)).toList();
            postingList.forEach(postingDto -> postingDto.setPostingList(null));
            response.setPostingList(postingList);
        }
        return response;
    }
}
