package hello.wantedpreonboarding.controller;

import hello.wantedpreonboarding.dto.PostingDto;
import hello.wantedpreonboarding.dto.response.PostingResponseDto;
import hello.wantedpreonboarding.mapper.PostingMapper;
import hello.wantedpreonboarding.service.PostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/read/{postingId}")
    public ResponseEntity<?> readPosting
            (@PathVariable("postingId") Integer postingId) {
        PostingDto dto = postingService.readPosting(postingId);
        PostingResponseDto response = PostingMapper.toResponse(dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/read/list")
    public ResponseEntity<?> readPostingList
            (@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostingDto> dtos = postingService.readPostingList(pageable);
        Page<PostingResponseDto> response = dtos.map(PostingMapper::toResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/update/{postingId}")
    public ResponseEntity<?> updatePosting
            (@PathVariable("postingId") Integer postingId,
            @RequestBody PostingDto request) {
        PostingDto dto = postingService.updatePosting(postingId, request);
        PostingResponseDto response = PostingMapper.toResponse(dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{postingId}")
    public ResponseEntity<?> deletePosting
            (@PathVariable("postingId") Integer postingId) {
        postingService.deletePosting(postingId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
