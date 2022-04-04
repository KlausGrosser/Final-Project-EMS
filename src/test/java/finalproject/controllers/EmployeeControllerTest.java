/*
package finalproject.controllers;

import finalproject.models.Employee;
import finalproject.models.JobLevel;
import finalproject.models.Office;
import finalproject.repositories.EmployeeRepository;
import finalproject.services.EmployeeService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.assertj.core.api.Assertions.assertThat;


import static finalproject.models.JobLevel.JUNIOR;
import static finalproject.models.Office.BERLIN;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

//@RunWith --> JUnit will invoke the class it references to run the tests in that class instead of the runner built into JUnit.
// @WebMvcTest annotation to fire up an application context that contains only the beans needed for testing a web controller:

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = EmployeeController.class)
//@AutoConfigureMockMvc(secure=false)
public class EmployeeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private EmployeeController employeeController;


  //@MockBean to add mock objects to the Spring application context. The mock will replace any existing bean of the same type in the application context.
  @MockBean
  private EmployeeService employeeService;

  Employee employee1;
  @Before
  public void setUpEmployee() throws Exception{
  Employee employee1 = new Employee();
  employee1.setFName("Alice");
  employee1.setLName("Mutke");
  employee1.setBirthDate(1980-02-27);
  employee1.setHireDate(2022-04-04);
  employee1.setIBAN(182918928);
  employee1.setSocialSecurityNum(343847);
  employee1.setTaxId(45435);
  employee1.setHolidays(28);
  employee1.setSalary(4000);
  employee1.setJobLevel(JUNIOR);
  employee1.setOffice(BERLIN);
  }

  @Test
  public void testList() throws Exception{
    assertThat(this.employeeService).isNotNull();
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employee"))
            .andExpect(status().isOk())
            .andExpect(view().name("employees"))
            .andExpect(view().name("employees"))
            .andDo(print());
  }
  }

*/
