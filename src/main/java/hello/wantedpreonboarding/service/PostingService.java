package hello.wantedpreonboarding.service;

import hello.wantedpreonboarding.dto.PostingDto;
import hello.wantedpreonboarding.entity.Company;
import hello.wantedpreonboarding.entity.Posting;
import hello.wantedpreonboarding.mapper.PostingMapper;
import hello.wantedpreonboarding.repository.CompanyRepository;
import hello.wantedpreonboarding.repository.PostingRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        List<PostingDto> dtoList = new ArrayList<>();
        List<Posting> list = postingRepository.findAllByCompany(posting.getCompany());
        if (!list.isEmpty()) {
            dtoList = list.stream().map(PostingMapper::toDto).collect(Collectors.toList());
        }

        PostingDto postingDto = PostingMapper.toDto(posting);
        postingDto.setPostingList(dtoList);
        return postingDto;
    }

    public Page<PostingDto> readPostingList(Pageable pageable, String keyword) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Specification<Posting> spec = search(keyword);
        Page<Posting> founds = postingRepository.findAll(spec, pageable);
        return founds.map(PostingMapper::toDto);
    }

    private Specification<Posting> search(String keyword) {
        return new Specification<>() {
            @Serial
            private final static long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Posting> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true); // 중복 제거
                Join<Posting, Company> company = root.join("company", JoinType.LEFT);

                return cb.or(cb.like(root.get("title"), "%" + keyword + "%"),
                        cb.like(root.get("content"), "%" + keyword + "%"),
                        cb.like(root.get("position"), "%" + keyword + "%"),
                        cb.like(root.get("stack"), "%" + keyword + "%"),
                        cb.like(root.get("region"), "%" + keyword + "%"),
                        cb.like(root.get("position"), "%" + keyword + "%"),
                        cb.like(company.get("name"), "%" + keyword + "%"),
                        cb.like(company.get("description"), "%" + keyword + "%"),
                        cb.like(company.get("industry"), "%" + keyword + "%")
                );
            }
        };
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
