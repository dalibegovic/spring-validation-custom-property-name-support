package com.example.validation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
class AccountResourceTest {

  private final MockMvc mockMvc;

  @Autowired
  public AccountResourceTest(MockMvc mockMvc) {
    this.mockMvc = mockMvc;
  }

  @Test
  void createAccountFirstNameMustBeSet() throws Exception {
    mockMvc.perform(post("/account")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "personal_data": {
                    "first_name": ""
                  }
                }
                """))
        .andExpect(status().isBadRequest());
  }
}
