package com.finalproject.model.service;

import com.finalproject.dto.CompanyDTO;
import com.finalproject.dto.RegistrationUserDTO;
import com.finalproject.dto.UpdateUserDTO;
import com.finalproject.dto.UpdateUserProfileDTO;
import com.finalproject.model.entity.Activity;
import com.finalproject.model.entity.Authority;
import com.finalproject.model.entity.Company;
import com.finalproject.model.entity.User;
import com.finalproject.model.repository.CompanyRepository;
import com.finalproject.util.exception.UsernameNotUniqueException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Log4j2
@Service
public class CompanyService {

    CompanyRepository companyRepository;


    public Company createCompany(CompanyDTO companyDTO) {
        Company company = Company
                .builder()
                .name(companyDTO.getName())
                .CEO(companyDTO.getCEO())
                .address(companyDTO.getAddress())
                .build();

            companyRepository.save(company);
            //log.info("New company " + company);

        return company;
    }

//
//    public Page<Company> findAllCompaniesPageable(Pageable pageable) {
//        return companyRepository.findAll(pageable);
//    }

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

    public Company findByCompanyName(String name){
        return companyRepository.findByName(name)
                .orElseThrow(()-> new UsernameNotFoundException(name));
    }

    //Get company by keyword
    public Page<Company> findByKeyword(Pageable pageable, String keyword) {
        return companyRepository.findByKeyword(keyword, pageable);
    }

    public Page<Company> findAllCompaniesPageable(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }
}
