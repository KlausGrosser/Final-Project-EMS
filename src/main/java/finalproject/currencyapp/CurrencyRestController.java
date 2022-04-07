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

  @PostMapping("/currency_input")
  public String save(Currency currency, Model model) {
    model.addAttribute("currency", currency);
    return "currency_saved";
  }



  @GetMapping("/currency_output")
  public String currencyConverter1() {
    return "currency_output";
  }

  @PostMapping("/currency_output")
  public String getResult(@RequestParam String base_code, @RequestParam String target_code, @RequestParam double amount) {

    currencyRestService.getConvertedAmount(base_code, target_code, amount);
    return "redirect:/currency_output";
  }

  @GetMapping("/currency_output/{base_code}/{target_code}/{amount}")
  public Double getConversion(@PathVariable(value = "base_code") String base_code,
                              @PathVariable(value = "target_code") String target_code,
                              @PathVariable(value = "amount") double amount) {

    return currencyRestService.getConvertedAmount(base_code, target_code, amount);

  }

}




