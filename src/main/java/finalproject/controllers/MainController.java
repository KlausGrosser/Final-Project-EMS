package finalproject.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

  @GetMapping(path = "api/v1")
  public String home (){
    return "index";
  }

}
