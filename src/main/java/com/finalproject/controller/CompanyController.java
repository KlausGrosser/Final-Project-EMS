package com.finalproject.controller;

import com.finalproject.dto.CompanyDTO;
import com.finalproject.model.service.CompanyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@Log4j2
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    //Get Mapping
    @GetMapping()
    public String getListOfCompanies(Model model, String keyword,
                                     @PageableDefault(size = 15,
                                             sort = {"name", "ceo"}) Pageable pageable) {

        if (keyword != null) {
            model.addAttribute("companies", companyService.findByKeyword(pageable, keyword));
        } else {
            model.addAttribute("companies", companyService.findAllCompaniesPageable(pageable));
        }
        return "companies";
    }

    @GetMapping("/add")
    public String getAddCompanyPage(@ModelAttribute("company")CompanyDTO companyDTO) {
        return "create_company";
    }

    @GetMapping("/delete/{id}")
    public String deleteCompany(@PathVariable("id") long longId) {
        companyService.deleteCompany(longId);
        return "redirect:/companies";
    }


    //Post Mapping
    @PostMapping("/add")
    public String addCompany(@ModelAttribute("company") @Valid CompanyDTO companyDTO,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "create_company";
        }

        companyService.createCompany(companyDTO);
        return "companies";
    }
}





