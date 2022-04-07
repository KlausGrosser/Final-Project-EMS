package finalproject.currencyapp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Controller
@RequiredArgsConstructor
public class CurrencyRestController {

  private final CurrencyRestService currencyRestService;

  @GetMapping(path = "/currency_input")
  public String currencyConverter(Model model){
    return "currency_input";
  }

  @GetMapping("/currency_output")
  public String currencyConverter1 () {
    return "currency_output";
  }

  @PostMapping("/currency_output")
  public String getResult(@RequestParam String base_code, @RequestParam String target_code, @RequestParam double amount) {

        currencyRestService.getConvertedAmount(base_code, target_code, amount);
        return "redirect:/currency_output";
  }

  @GetMapping("/currency_output/{base_code}/{target_code}/{amount}")
  public Double getConversion (@PathVariable (value = "base_code") String base_code,
                               @PathVariable (value = "target_code") String target_code,
                               @PathVariable (value = "amount") double amount){

    return currencyRestService.getConvertedAmount(base_code, target_code, amount);

  }

/*  @GetMapping(value = "/currency_input")
  public String populateList(Model model) {
    List<String> options = new ArrayList<String>();
    options.add("EUR");
    options.add("USD");
    options.add("MXP");
    model.addAttribute("options", options);
    return "dropDownList.html";*/

  @GetMapping("/currency")
  public String getStudentCourse(Model model) {
    Set<String> currencies = currencyRestService.getAvailableCurrencies();

    model.addAttribute("currencies", currencies);

    return "currency_input";
  }
  }
}



