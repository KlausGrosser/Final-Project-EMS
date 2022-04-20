package com.finalproject.controller;

import com.finalproject.dto.CompanyDTO;
import com.finalproject.model.service.CompanyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@Log4j2
@RequestMapping
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    //Get Mapping
    @GetMapping("/companies")
    public String getCompaniesPage(Model model,
                                   @PageableDefault(sort = {"id"},
                                           direction = Sort.Direction.DESC,
                                           size = 5) Pageable pageable, String company) {

        if (company != null) {
            model.addAttribute("company", companyService.findByCompanyName(company));
        } else {
            model.addAttribute("company", companyService.findAllCompaniesPageable(pageable));
        }
        return "companies";
    }



    @GetMapping("/company/add")
    public String getAddCompanyPage(@ModelAttribute("company")CompanyDTO companyDTO) {
        return "create_company";
    }

    @GetMapping("/company/delete/{id}")
    public String deleteActivity(@PathVariable("id") long longId) {
        companyService.deleteCompany(longId);
        return "delete_company";
    }


    //Post Mapping
    @PostMapping("/company/add")
    public String addCompany(@ModelAttribute("company") @Valid CompanyDTO companyDTO) {

        companyService.createCompany(companyDTO);
        return "redirect:/companies";
    }
}





