package hello.wantedpreonboarding.controller;

import hello.wantedpreonboarding.dto.CompanyDto;
import hello.wantedpreonboarding.dto.response.CompanyResponseDto;
import hello.wantedpreonboarding.mapper.CompanyMapper;
import hello.wantedpreonboarding.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping("/{companyName}")
    public ResponseEntity<?> getCompany(
            @PathVariable("companyName") String companyName) {
        CompanyDto found = companyService.getCompanyByName(companyName);
        CompanyResponseDto response = CompanyMapper.toResponse(found);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/")
    public ResponseEntity<?> getCompanyList(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CompanyDto> dtos = companyService.getCompanyList(pageable);
        Page<CompanyResponseDto> response = dtos.map(CompanyMapper::toResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
