package hello.wantedpreonboarding.controller;

import hello.wantedpreonboarding.dto.PostingDto;
import hello.wantedpreonboarding.dto.request.PostingCreateRequestDto;
import hello.wantedpreonboarding.dto.response.PostingResponseDto;
import hello.wantedpreonboarding.mapper.PostingMapper;
import hello.wantedpreonboarding.service.PostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posting")
@RequiredArgsConstructor
public class PostingController {
    private final PostingService postingService;

    @PostMapping("/create")
    public ResponseEntity<?> createPosting(
            @RequestBody PostingDto request) {
        PostingDto dto = postingService.create(request);
        PostingResponseDto response = PostingMapper.toResponse(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
