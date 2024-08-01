package hello.wantedpreonboarding.dto.request;

import hello.wantedpreonboarding.dto.response.PostingResponseDto;
import hello.wantedpreonboarding.dto.response.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationCreateRequestDto {
    private String username;
    private Integer postingId;
}
