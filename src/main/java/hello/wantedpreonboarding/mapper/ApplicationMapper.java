package hello.wantedpreonboarding.mapper;

import hello.wantedpreonboarding.dto.ApplicationDto;
import hello.wantedpreonboarding.entity.Application;

public class ApplicationMapper {
    public static ApplicationDto toDto(Application entity) {
        return ApplicationDto.builder()
                .id(entity.getId())
                .user(UserMapper.toDto(entity.getUser()))
                .posting(PostingMapper.toDto(entity.getPosting()))
                .build();
    }
}
