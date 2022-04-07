/*
package finalproject.currencyapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CurrencyConfig {

  @Bean
  CommandLineRunner commandLineRunner (
          CurrencyRepository repository{
            return args -> {
              Currency currency1 = new Currency(
                      "USD"
              );
              Currency currency2 = new Currency(
                      "EUR"
              );

              Currency currency3 = new Currency(
                      "MXP"
              );
            repository.saveAll(
                    List.of(currency1, currency2, currency3)
            );
            };
  }

}
*/
