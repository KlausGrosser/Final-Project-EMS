/*
package finalproject.controllers;


import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

//Purpose of testing is to validate that each unit of the software performs as design
//a unit is the smallest testable part of any software

@RunWith(SpringRunner.class)
class MainControllerTest {

  private MockMvc mockMvc;

  @Before
  public void setUp(){
    mockMvc = MockMvcBuilders.standaloneSetup(new MainController()).build();
  }

  @Test
  public void testIndex() throws Exception{
       mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/home"))
            .andExpect(status().isOk())
            .andExpect(view().name("index"))
            .andDo(print());

  }
}*/
