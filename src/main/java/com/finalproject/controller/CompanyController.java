package com.finalproject.controller;

import com.finalproject.dto.CompanyDTO;
import com.finalproject.dto.LeaveDTO;
import com.finalproject.dto.RegistrationUserDTO;
import com.finalproject.model.entity.LeaveReason;
import com.finalproject.model.service.CompanyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

//    @GetMapping("/companies")
//    public String getListOfCompanies(Model model, String keyword,
//                                 @PageableDefault(size = 15,
//                                         sort = {"name"}) Pageable pageable) {
//
//        model.addAttribute("companies", companyService.findAllCompaniesPageable(pageable));
//
//        return "companies";
//    }
//
//
//    @GetMapping("/company/delete/{id}")
//    public String deleteCompany(@PathVariable("id") long id) {
//        companyService.deleteCompany(id);
//        return "redirect:companies";
//    }
//
//    @GetMapping("/create_company")
//    public String getCompanyPage(@ModelAttribute("company") CompanyDTO companyDTO) {
//        return "create_company";
//    }
//
//    @PostMapping("/create_company")
//    public String createNewCompany(@ModelAttribute("company") @Valid CompanyDTO companyDTO) {
//        companyService.createCompany(companyDTO);
//        return "companies";
//    }
//
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(IllegalArgumentException.class)
//    public String handleIllegalArgumentException(IllegalArgumentException e) {
//        log.error(e.getMessage());
//        return "error/404";
//    }

    //Get Mapping
    @GetMapping("/companies")
    public String getLeavesPage(Model model,
                                @PageableDefault(sort = {"id"},
                                        direction = Sort.Direction.DESC,
                                        size = 5) Pageable pageable) {
        model.addAttribute("companies", companyService.findAllCompaniesPageable(pageable));
        return "companies";
    }

    @GetMapping("/company/add")
    public String getAddLeavePage(@ModelAttribute("companies") CompanyDTO companyDTO) {

        return "create_company";
    }


    //Post Mapping
    @PostMapping("/company/add")
    public String addLeave(@ModelAttribute("companies") @Valid CompanyDTO companyDTO, BindingResult bindingResult, Model model) {

        companyService.createCompany(companyDTO);

        return "redirect:/companies";
    }

}
