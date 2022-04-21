package com.finalproject.model.service;

import com.finalproject.dto.CompanyDTO;
import com.finalproject.model.entity.Company;
import com.finalproject.model.repository.CompanyRepository;
import com.finalproject.util.exception.UsernameNotUniqueException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Log4j2
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final MessageSource messageSource;


    @Autowired
    public CompanyService(CompanyRepository companyRepository, MessageSource messageSource) {
        this.companyRepository = companyRepository;
        this.messageSource = messageSource;
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

    public Company findByCompanyName(String name){
        return companyRepository.findByName(name)
                .orElseThrow(()-> new UsernameNotFoundException(name));
    }


    public Company findById(long id) {
        return companyRepository.findById(id).orElseThrow();
    }

    public void updateCompany(long id, CompanyDTO companyDTO) {
        Company company = getCompanyById(id);

        if(Objects.nonNull(company.getName())){
            company.setName(companyDTO.getName());
        }

        if(Objects.nonNull(company.getAddress())){
            company.setAddress(company.getAddress());
        }

        if(Objects.nonNull(company.getCeo())){
            company.setCeo(companyDTO.getCeo());
        }

        try{
            companyRepository.save(company);
        } catch (DataIntegrityViolationException e) {
            log.error("Company not unique: " + companyDTO.getName());
            throw new UsernameNotUniqueException(messageSource.getMessage(
                    "users.registration.login.not_unique",
                    null,
                    LocaleContextHolder.getLocale()) + companyDTO.getName(), e);
        }
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }
}