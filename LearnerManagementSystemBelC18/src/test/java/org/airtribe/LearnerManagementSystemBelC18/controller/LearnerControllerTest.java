package org.airtribe.LearnerManagementSystemBelC18.controller;

import org.airtribe.LearnerManagementSystemBelC18.entity.Learner;
import org.airtribe.LearnerManagementSystemBelC18.entity.LearnerDTO;
import org.airtribe.LearnerManagementSystemBelC18.entity.LearnerResponseDTO;
import org.airtribe.LearnerManagementSystemBelC18.service.LearnerManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class LearnerControllerTest {

  @MockitoBean
  private LearnerManagementService _learnerManagementService;

  @Autowired
  private MockMvc _mockMvc;

  private Learner _learner;

  private LearnerResponseDTO _learnerDTO;

  @BeforeEach
  public void setup() {
    _learner = new Learner("test", "test3@gmail.com", "1234");
    _learnerDTO = new LearnerResponseDTO("test", "test3@gmail.com", "1234");
  }

  @Test
  public void testSaveLearner_Successfully() throws Exception {
    // ARRANGE ACT ASSERT
    when(_learnerManagementService.createLearner(any())).thenReturn(_learner);
    when(_learnerManagementService.toLearnerResponseDTO(any())).thenReturn(_learnerDTO);

    // ACT && ASSERT
    _mockMvc.perform(MockMvcRequestBuilders.post("/learners")
        .contentType("application/json").content("{\"learnerName\":\"test\",\"learnerEmail\":\"test3@gmail.com\",\"learnerPhone\":\"1234\"}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.learnerName").value("test"))
        .andExpect(jsonPath("$.learnerEmail").value("test3@gmail.com"))
        .andExpect(jsonPath("$.learnerPhone").value("1234"))
        .andDo(print());
  }

  @Test
  public void testSaveLearner_FailsValidation() throws Exception {
    _mockMvc.perform(MockMvcRequestBuilders.post("/learners")
            .contentType("application/json").content("{\"learnerName\":\"test\",\"learnerEmail\":\"test3\",\"learnerPhone\":\"1234\"}"))
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.learnerEmail").value("must be a well-formed email address"))
        .andDo(print());
  }
}
