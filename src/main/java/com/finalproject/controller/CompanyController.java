package com.finalproject.controller;
import com.finalproject.dto.CompanyDTO;
import com.finalproject.dto.UpdateUserDTO;
import com.finalproject.model.entity.Authority;
import com.finalproject.model.entity.Company;
import com.finalproject.model.entity.Department;
import com.finalproject.model.service.CompanyService;
import com.finalproject.util.exception.UsernameNotUniqueException;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@Log4j2
//@RequestMapping
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
    public String deleteCompany(@PathVariable("id") long longId) {
        companyService.deleteCompany(longId);
        return "companies";
    }

    @GetMapping("/company/update/{id}")
    public String updateCompany(@PathVariable("id") long id, Model model) {
        Company company = companyService.findById(id);

        model.addAttribute("company", company);

        return "update_company";
    }


    //Post Mapping
    @PostMapping("/company/add")
    public String addCompany(@ModelAttribute("company") @Valid CompanyDTO companyDTO) {

        companyService.createCompany(companyDTO);
        return "redirect:/companies";
    }

    @PostMapping("/company/update/{id}")
    public String updateCompany(@PathVariable("id") long id,
                                @ModelAttribute("company") @Valid CompanyDTO companyDTO,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            return "update_company";
        }

        try {
            companyService.updateCompany(id, companyDTO);
        } catch (UsernameNotUniqueException e) {
            model.addAttribute("usernameErrorMessage", e.getMessage());
            model.addAttribute("authorities", Authority.values());
            return "update-user";
        }

        return "redirect:/companies";
    }
}