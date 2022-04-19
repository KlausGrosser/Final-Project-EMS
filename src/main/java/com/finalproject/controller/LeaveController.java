package com.finalproject.controller;

import com.finalproject.dto.LeaveDTO;
import com.finalproject.model.entity.LeaveReason;
import com.finalproject.model.service.LeaveService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@Log4j2
@Controller
public class LeaveController {

    private final LeaveService leaveService;


    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;

    }

    //Get Mapping
    @GetMapping("/leaves")
    public String getLeavesPage(Model model,
                                @PageableDefault(sort = {"id"},
                                        direction = Sort.Direction.DESC,
                                        size = 5) Pageable pageable) {
        model.addAttribute("leavePage", leaveService.findAllLeavesPageable(pageable));
        return "leaves";
    }

    @GetMapping("/leaves/add")
    public String getAddLeavePage(Model model,
                                  @ModelAttribute("leave") LeaveDTO leaveDTO) {
        model.addAttribute("reasons", LeaveReason.values());

        return "add-leave";
    }


    //Post Mapping
    @PostMapping("/leaves/add")
    public String addLeave(@ModelAttribute("leave") @Valid LeaveDTO leaveDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("reasons", LeaveReason.values());
            return "add-leave";
        }

        leaveService.createLeave(leaveDTO);

        return "redirect:/leaves";
    }
}




//
//    @GetMapping(value = "/leaves/add")
//    public ModelAndView applyLeave(ModelAndView mav) {
//
//        mav.addObject("leave", new Leave());
//        mav.setViewName("add-leave");
//        return mav;
//    }

//    @PostMapping(value = "/leaves/add")
//    public ModelAndView submitApplyLeave(ModelAndView mav, @Valid Leave leave,
//                                         BindingResult bindingResult) {
//
//        User userInfo = userService.getCurrentUser();
//        if (bindingResult.hasErrors()) {
//            mav.setViewName("add-leave");
//        } else {
//            leave.setUsername(userInfo.getUsername());
//            leaveService.applyLeave(leave);
//            mav.addObject("successMessage", "Your Leave Request is registered!");
//            //mav.setView(new RedirectView("/home"));
//        }
//        return mav;
//    }

//
//    @PostMapping("/leaves/approve/{id}")
//    public String approveLeaveRequest(@PathVariable("id") long leaveId) {
//        Leave leave = leaveService.findLeaveById(leaveId);
//
//        if (!leave.getLeaveStatus().equals(LeaveStatus.PENDING)) {
//            return "redirect:/leaves";
//        }
//        leaveService.approveLeaveRequest(leaveId);
//
//        return "redirect:leaves";
//    }

//    @PostMapping("/leaves/reject/{id}")
//    public String rejectLeaveRequest(@PathVariable("id") long leaveId) {
//        Leave leave = leaveService.findLeaveById(leaveId);
//
//        if (!leave.getLeaveStatus().equals(LeaveStatus.PENDING)) {
//            return "redirect:/leaves";
//        }
//
//        leaveService.rejectLeaveRequest(leaveId);
//
//        return "redirect:leaves";
//    }
//
//

