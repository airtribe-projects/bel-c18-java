package org.airtribe.LearnerManagementSystemBelC18.integTest;

import org.airtribe.LearnerManagementSystemBelC18.entity.Learner;
import org.airtribe.LearnerManagementSystemBelC18.entity.LearnerDTO;
import org.airtribe.LearnerManagementSystemBelC18.entity.LearnerResponseDTO;
import org.airtribe.LearnerManagementSystemBelC18.repository.CohortRepository;
import org.airtribe.LearnerManagementSystemBelC18.repository.LearnerRepository;
import org.aspectj.lang.annotation.After;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class LearnerManagementSystemIntegTest {

  @Autowired
  private MockMvc _mockMvc;

  private LearnerResponseDTO _learnerDTO;

  private Learner _learner;

  @Autowired
  private LearnerRepository _learnerRepository;

  @BeforeEach
  public void setup() {
    _learner = new Learner("test", "test3@gmail.com", "1234");
    _learnerDTO = new LearnerResponseDTO("test", "test3@gmail.com", "1234");
    _learnerRepository.deleteAll();

  }

  @AfterEach
  public void cleanup() {
   _learnerRepository.deleteAll();
//    _cohortRepository.deleteAll();
  }

  @Test
  public void testSaveLearner_Successfully() throws Exception {

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
  public void testSaveLearner_doesNotAllowDuplicateEmailCreation() throws Exception {
    // ARRANGE
    _learnerRepository.save(_learner);

    _mockMvc.perform(MockMvcRequestBuilders.post("/learners")
            .contentType("application/json").content("{\"learnerName\":\"test\",\"learnerEmail\":\"test3@gmail.com\",\"learnerPhone\":\"1234\"}"))
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message").value(Matchers.containsString("Data integrity violation")))
        .andDo(print());
  }
}


//1 2 3 4
//
//1 2 3 4
