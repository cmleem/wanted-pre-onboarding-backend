package hello.wantedpreonboarding.mapper;

import hello.wantedpreonboarding.dto.UserDto;
import hello.wantedpreonboarding.dto.response.UserResponseDto;
import hello.wantedpreonboarding.entity.User;

public class UserMapper {
    public static UserDto toDto(User entity) {
        if (entity == null) return null;
        return UserDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .position(entity.getPosition())
                .career(entity.getCareer())
                .build();
    }

    public static UserResponseDto toResponse(UserDto user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .position(user.getPosition())
                .career(user.getCareer())
                .build();
    }
}
