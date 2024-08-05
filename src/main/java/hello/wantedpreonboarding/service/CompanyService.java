package hello.wantedpreonboarding.service;

import hello.wantedpreonboarding.dto.CompanyDto;
import hello.wantedpreonboarding.entity.Company;
import hello.wantedpreonboarding.mapper.CompanyMapper;
import hello.wantedpreonboarding.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyDto findCompany(Integer companyId) {
        Company company = getCompany(companyId);
        return CompanyMapper.toDto(company);
    }

    public Page<CompanyDto> findCompanyList(Pageable pageable) {
        Page<Company> all = companyRepository.findAll(pageable);
        return all.map(CompanyMapper::toDto);
    }

    private Company getCompany(Integer companyId) {
        return companyRepository.findById(companyId).orElseThrow(() -> new IllegalArgumentException("Company with id " + companyId + " not found"));
    }
}
