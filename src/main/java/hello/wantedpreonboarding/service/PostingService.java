package hello.wantedpreonboarding.service;

import hello.wantedpreonboarding.dto.PostingDto;
import hello.wantedpreonboarding.entity.Company;
import hello.wantedpreonboarding.entity.Posting;
import hello.wantedpreonboarding.entity.enums.PositionType;
import hello.wantedpreonboarding.entity.enums.RegionType;
import hello.wantedpreonboarding.mapper.PostingMapper;
import hello.wantedpreonboarding.repository.CompanyRepository;
import hello.wantedpreonboarding.repository.PostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostingService  {
    private final PostingRepository postingRepository;
    private final CompanyRepository companyRepository;

    public PostingDto create(PostingDto dto, String companyName) {
        Company company = companyRepository.findByName(companyName).orElseThrow(() -> new IllegalArgumentException("Company with name " + companyName + " not found"));

        Posting posting = Posting.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .position(dto.getPosition())
                .incentive(dto.getIncentive())
                .deadLine(dto.getDeadline())
                .stack(dto.getStack())
                .region(dto.getRegion())
                .company(company)
                .build();
        Posting saved = postingRepository.save(posting);
        return PostingMapper.toDto(saved);
    }

    public PostingDto readPosting(Integer postingId) {
        Posting posting = getPosting(postingId);
        return PostingMapper.toDto(posting);
    }

    public Page<PostingDto> readPostingList(Pageable pageable) {
        Page<Posting> all = postingRepository.findAll(pageable);
        return all.map(PostingMapper::toDto);
    }

    public PostingDto updatePosting(Integer postingId, PostingDto dto) {
        Posting posting = getPosting(postingId);
        posting.updateTitle(dto.getTitle());
        posting.updateContent(dto.getContent());
        posting.updatePosition(dto.getPosition());
        posting.updateIncentive(dto.getIncentive());
        posting.updateDeadLine(dto.getDeadline());
        posting.updateStack(dto.getStack());
        posting.updateRegion(dto.getRegion());
        return PostingMapper.toDto(posting);
    }

    public void deletePosting(Integer postingId) {
        Posting posting = getPosting(postingId);
        postingRepository.delete(posting);
    }

    private Posting getPosting(Integer postingId) {
        Posting posting = postingRepository.findById(postingId).orElseThrow(() -> new IllegalArgumentException("Posting with id " + postingId + " not found"));
        return posting;
    }
}
