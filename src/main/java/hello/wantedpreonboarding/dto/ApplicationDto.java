package hello.wantedpreonboarding.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ApplicationDto {
    private Integer id;
    private UserDto user;
    private PostingDto posting;
    private LocalDateTime createdAt;
}
