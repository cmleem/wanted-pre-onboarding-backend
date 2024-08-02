package hello.wantedpreonboarding.controller;

import hello.wantedpreonboarding.dto.ApplicationDto;
import hello.wantedpreonboarding.dto.response.ApplicationResponseDto;
import hello.wantedpreonboarding.mapper.ApplicationMapper;
import hello.wantedpreonboarding.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final ApplicationService applicationService;

    // 지원하기
    @PostMapping("/apply")
    public ResponseEntity<?> apply
    (@RequestParam("username") String username,
     @RequestParam("postingId") Integer postingId
    ) {
        ApplicationDto application = applicationService.createApplication(username, postingId);
        ApplicationResponseDto response = ApplicationMapper.toResponse(application);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 내 지원 확인
    @GetMapping("/applications")
    public ResponseEntity<?> getMyApplicants(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<ApplicationDto> dtos = applicationService.readApplicationList(pageable);
        Page<ApplicationResponseDto> response = dtos.map(ApplicationMapper::toResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 지원 목록 확인
    @GetMapping("/application/{applicationId}")
    public ResponseEntity<?> getMyApplication(
            @PathVariable("applicationId") Integer applicationId
    ) {
        ApplicationDto dto = applicationService.readApplicationById(applicationId);
        ApplicationResponseDto responseDto = ApplicationMapper.toResponse(dto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 지원 삭제
    @DeleteMapping("/applications/{applicationId}")
    public ResponseEntity<?> deleteMyApplication(
            @PathVariable("applicationId") Integer applicationId
    ) {
        applicationService.deleteApplication(applicationId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
