package hello.wantedpreonboarding.dto;

import hello.wantedpreonboarding.entity.enums.CareerType;
import hello.wantedpreonboarding.entity.enums.PositionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserDto {
    private Integer id;
    private String username;
    private String email;
    private CareerType career;
    private PositionType position;
    private Boolean exposed;
}
