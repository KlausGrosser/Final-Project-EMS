package finalproject.controllers;

import finalproject.currencyapp.Currency;
import finalproject.models.Employee;
import finalproject.models.LeaveDetails;
import finalproject.repositories.RoleRepository;
import finalproject.security.email.EmailService;
import finalproject.services.DepartmentService;
import finalproject.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class MainController {

  private final EmployeeService employeeService;
  private final DepartmentService departmentService;
  private final EmailService emailService;
  private final RoleRepository roleRepository;;

  @GetMapping("/login")
  public String login(){
    return "login";
  }

  @GetMapping(path = "/")
  public String home (){
    return "index";
  }

  @GetMapping(path = "/registration")
  public String register(Model model){
    model.addAttribute("employee", new Employee());
    return "registration_page";
  }

  @GetMapping(path = "/new")
  public String getAddDoctorView(Model model){
    model.addAttribute("listDepartments", departmentService.getAllDepartments());
    model.addAttribute("listEmployeeRoles", roleRepository.findAll());
    model.addAttribute("employee", new Employee());
    return "new_employee";
  }

  @GetMapping(path = "/confirm")
  public String confirm(@RequestParam("token") String token){
    emailService.confirmToken(token);
    return "login";
  }

  @PostMapping(path = "/registerNewDoc")
  public String register(@ModelAttribute Employee request){
    emailService.registerMail(new Employee(
            request.getFName(),
            request.getLName(),
            request.getEmail(),
            request.getPassword()
    ));
    return "register_check_email";
  }

  @GetMapping(path = "employees/page/{pageNo}")
  public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                              @RequestParam("sortField") String sortField,
                              @RequestParam("sortDir") String sortDir,
                              Model model) {
    int pageSize = 5;

    Page<Employee> page = employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);
    List<Employee> listDoctors = page.getContent();

    model.addAttribute("currentPage", pageNo);
    model.addAttribute("totalPages", page.getTotalPages());
    model.addAttribute("totalItems", page.getTotalElements());

    model.addAttribute("sortField", sortField);
    model.addAttribute("sortDir", sortDir);
    model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

    model.addAttribute("listDoctors", listDoctors);
    return "employees_list";
  }

  @GetMapping(path = "/employees_list")
  public String listEmployees(Model model){
    return  findPaginated(1, "lastName", "asc", model);
  }

  @GetMapping(path = "/check_in_out")
  public String checkIn(){
    return "check_in_out";
  }

  @GetMapping(path = "/leave")
  public String leaveReasons(Model model) {
    model.addAttribute("leaveReasons", new LeaveDetails());
    return "leave";
  }

}
