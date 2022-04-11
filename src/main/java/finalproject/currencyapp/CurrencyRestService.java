package finalproject.currencyapp;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class CurrencyRestService {


  private final WebClient webClient;
  private String apiKey = "055fe50ac6aa8f7680c85af3";
 //private static final DecimalFormat df = new DecimalFormat("0.00");

  public CurrencyRestService() {
    webClient = WebClient.builder()
            .baseUrl("https://v6.exchangerate-api.com/v6/" + apiKey)
            .build();
  }


  public Double getConvertedAmount(String base_code, String target_code, double amount) {
              //return (df.format(double) webClient
              return (Double) webClient
              .get()
              .uri(uriBuilder -> uriBuilder
                      .path("/pair")
                      .path("/" + base_code)
                      .path("/" + target_code)
                      .path("/" + amount)
                      .queryParam("")
                      .build()
              )
              .retrieve() /// Perform the HTTP request and retrieve the response body
              .bodyToMono(Map.class)
              .map(map -> map.get("conversion_result"))
              .block();  /// returns the body as an array of User

    }
  }

 /* public Double getConvertedAmount(String base_code, String target_code, double amount) {
    String error_message = "";
    if (base_code == null || target_code == null || amount < 0.1) {
      error_message = "please enter an input into each field";
    } else {
      error_message = "Thank You";
    }
    return (Double) webClient
            .get()
            .uri(uriBuilder -> uriBuilder
                    .path("/pair")
                    .path("/" + base_code)
                    .path("/" + target_code)
                    .path("/" + amount)
                    .queryParam("")
                    .build()
            )
            .retrieve() /// Perform the HTTP request and retrieve the response body
            .bodyToMono(Map.class)
            .map(map -> map.get("conversion_result"))
            .block();  /// returns the body as an array of User

  }
}*/