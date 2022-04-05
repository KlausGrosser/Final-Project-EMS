package finalproject.currencyapp;

import org.springframework.stereotype.Service;
import finalproject.currencyapp.rest.Currency;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class CurrencyService {

  private final WebClient webClient;

  public CurrencyService(WebClient.Builder builder) {
    webClient = builder.baseUrl("https://localhost:8080/api/v1/employees").build();
  }

  public Currency getExchange(String baseCurrency, String exchangeCurrency) {
    return webClient
            .get()
            .uri(uriBuilder -> uriBuilder
                    .path("/latest")
                    .queryParam("rhpF9LjPmNUuuKIr2sAVpSgyHMPXRPDT1fRVmKSY") // put API key here
                    .queryParam("base_currency", baseCurrency)
                    .queryParam("currencies", exchangeCurrency)
                    .build())
            .retrieve() // Perform the HTTP request and retrieve the response body
            .bodyToMono(Currency.class)
            .block(); // returns the body as an array of User
  }
}

