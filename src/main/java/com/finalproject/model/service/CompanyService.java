package com.finalproject.model.service;


import com.finalproject.dto.CompanyDTO;
import com.finalproject.model.entity.Company;
import com.finalproject.model.repository.CompanyRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;


    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;

    }

    public void createCompany(CompanyDTO companyDTO) {

        Company company = Company
                .builder()
                .name(companyDTO.getName())
                .ceo(companyDTO.getCeo())
                .address(companyDTO.getAddress())
                .build();

        companyRepository.save(company);

    }

    // @JsonInclude(JsonInclude.Include.NON_ABSENT)
    public Page<Company> findAllCompaniesPageable(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    public Company getCompanyById(long id) {
        return companyRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid company id: " + id));
    }


    public void deleteCompany(long id) {
        Company company = getCompanyById(id);
        companyRepository.delete(company);
    }


    public void save(Company company){
        companyRepository.save(company);
    }

    public Page<Company> findByKeyword(Pageable pageable, String keyword){
        return companyRepository.findByKeyword(keyword, pageable);
    }


}