package finalproject.currencyapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyRestServiceTest {

  @BeforeAll
  public static void setUp(){
    CurrencyRestService client = new CurrencyRestService();
  }


  @Test
  public void correctConvertedAmount() {
    CurrencyRestService client = new CurrencyRestService();

    //given
    String baseCode ="EUR";
    String targetCode ="DKK";
    double amount = 100;

    //when
    Double convertedAmount = client.getConvertedAmount(baseCode, targetCode, amount);

    //then
    Assertions.assertNotNull(convertedAmount);
  }
}