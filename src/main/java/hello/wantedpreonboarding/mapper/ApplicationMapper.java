package hello.wantedpreonboarding.mapper;

import hello.wantedpreonboarding.dto.ApplicationDto;
import hello.wantedpreonboarding.dto.response.ApplicationResponseDto;
import hello.wantedpreonboarding.entity.Application;

public class ApplicationMapper {
    public static ApplicationDto toDto(Application entity) {
        return ApplicationDto.builder()
                .id(entity.getId())
                .user(UserMapper.toDto(entity.getUser()))
                .posting(PostingMapper.toDto(entity.getPosting()))
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public static ApplicationResponseDto toResponse(ApplicationDto dto) {
        return ApplicationResponseDto.builder()
                .id(dto.getId())
                .user(UserMapper.toResponse(dto.getUser()))
                .posting(PostingMapper.toResponse(dto.getPosting()))
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
