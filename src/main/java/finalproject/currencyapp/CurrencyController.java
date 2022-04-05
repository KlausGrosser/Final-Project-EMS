package finalproject.currencyapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value="/api")
public class CurrencyController {
  private final CurrencyService currencyService;

  CurrencyController(CurrencyService currencyService) {
    this.currencyService = currencyService;
  }

  @GetMapping("/currency_output")
  String getExchangeRates(
          @RequestParam(value = "amount") double amount,
          @RequestParam(value = "base_currency") String baseCurrency,
          @RequestParam(value = "currencies") String currencies,
          Model model) {
    Map<String, Object> exchangeRates = currencyService.getExchange(baseCurrency, currencies).getData().getAdditionalProperties();
    Map<String, Double> rates = new HashMap<>();

    for (String currentCurr : exchangeRates.keySet()) {
      double exchangeValue = 1;

      if(!currentCurr.equals(baseCurrency)) {
        String exchangeCode = exchangeRates.get(currentCurr).toString();
        exchangeCode = exchangeCode.substring(17, 24);
        exchangeValue = Double.parseDouble(exchangeCode);
      }
      rates.put(currentCurr, amount * exchangeValue);
    }

    model.addAttribute("amount", amount);
    model.addAttribute("base", baseCurrency);
    model.addAttribute("rates", rates);

    return "currency_output";
  }

}