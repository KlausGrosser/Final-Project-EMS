package finalproject.currencyapp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class CurrencyRestController {

  private final CurrencyRestService currencyRestService;

  @GetMapping(path = "/currency_input")
  public String currencyConverter(Model model) {
    model.addAttribute("currency", new Currency());
    return "currency_input";
  }


  @GetMapping("/currency_output")
  public String currencyConverterOutput() {
    return "currency_output";
  }

/*  @PostMapping("/currency_output")
  public String getResult(@RequestParam String base_code, @RequestParam String target_code, @RequestParam double amount) {

    currencyRestService.getConvertedAmount(base_code, target_code, amount);
    return "redirect:/currency_output";
  }*/



  @GetMapping("/currency_output/{base_code}/{target_code}/{amount}")
  public Double getConversion(@PathVariable(value = "base_code", required= true) String base_code,
                              @PathVariable(value = "target_code", required= true) String target_code,
                              @PathVariable(value = "amount") double amount) {
/*  if (amount == null) {
    return "/";
  }else if (target_code == null) {
    return "/";
  }*/
    return currencyRestService.getConvertedAmount(base_code, target_code, amount);
  }
}





