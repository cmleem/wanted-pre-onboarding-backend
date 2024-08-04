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

                return cb.or(cb.like(root.get("title"), "%" + keyword + "%"), // 채용공고 제목
                        cb.like(root.get("content"), "%" + keyword + "%"), // 채용공고 내용
                        cb.like(root.get("position"), "%" + keyword + "%"), // 채용공고 직무
                        cb.like(root.get("stack"), "%" + keyword + "%"), // 채용공고 스택
                        cb.like(root.get("region"), "%" + keyword + "%"), // 채용공고 지역
                        cb.like(company.get("name"), "%" + keyword + "%"), // 회사명
                        cb.like(company.get("description"), "%" + keyword + "%"), // 회사 소개
                        cb.like(company.get("industry"), "%" + keyword + "%") // 업종
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
        return postingRepository.findById(postingId).orElseThrow(() -> new IllegalArgumentException("Posting with id " + postingId + " not found"));
    }
}
