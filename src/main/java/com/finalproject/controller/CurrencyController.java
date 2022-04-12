package com.finalproject.controller;

import com.finalproject.model.entity.Currency;
import com.finalproject.model.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("/currency")
public class CurrencyController {

  private final CurrencyService currencyRestService;

  @GetMapping(path = "/currency_input")
  public String currencyConverter(Model model) {
    model.addAttribute("currency", new Currency());
    return "currency_input";
  }

  @GetMapping("/currency_output")
  public String currencyConverterOutput() {
    return "currency_output";
  }

  @GetMapping("/currency_output/{base_code}/{target_code}/{amount}")
  public Double getConversion(@PathVariable(value = "base_code", required= true) String base_code,
                              @PathVariable(value = "target_code", required= true) String target_code,
                              @PathVariable(value = "amount") double amount) {
    return currencyRestService.getConvertedAmount(base_code, target_code, amount);
  }
}





