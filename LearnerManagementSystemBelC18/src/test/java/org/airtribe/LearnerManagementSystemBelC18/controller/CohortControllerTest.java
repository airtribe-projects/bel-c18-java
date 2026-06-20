package org.airtribe.LearnerManagementSystemBelC18.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.airtribe.LearnerManagementSystemBelC18.entity.Cohort;
import org.airtribe.LearnerManagementSystemBelC18.entity.CohortResponseDTO;
import org.airtribe.LearnerManagementSystemBelC18.service.LearnerManagementService;
import org.airtribe.LearnerManagementSystemBelC18.util.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class CohortControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LearnerManagementService _learnerManagementService;

    private Cohort _cohort;


    private CohortResponseDTO _cohortResponse;


    @BeforeEach
    void setup(){
        _cohort = new Cohort("test","testDesc");
        _cohortResponse = new CohortResponseDTO("test", "testDesc");
    }

    @Test
    void testCreateCohort() throws Exception {
        Mockito.when(_learnerManagementService.createCohort(Mockito.any(Cohort.class)))
                .thenReturn(_cohort);
        Mockito.when(_learnerManagementService.toCohortResponseDTO(Mockito.any(Cohort.class)))
                .thenReturn(_cohortResponse);
        String cohortJson = JsonUtil.convertToJson(_cohort);
        mockMvc.perform(MockMvcRequestBuilders.post("/cohorts")
                .contentType("application/json").content(cohortJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.cohortName").value(_cohortResponse.getCohortName()))
                .andExpect(jsonPath("$.cohortDescription").value(_cohortResponse.getCohortDescription()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testFetchCohortById() throws Exception {
        Mockito.when(_learnerManagementService.fetchCohortById(Mockito.anyLong()))
                .thenReturn(_cohort);
        Mockito.when(_learnerManagementService.toCohortResponseDTO(Mockito.any(Cohort.class)))
                .thenReturn(_cohortResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/cohorts/{cohortId}",1L)
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.cohortName").value(_cohortResponse.getCohortName()))
                .andExpect(jsonPath("$.cohortDescription").value(_cohortResponse.getCohortDescription()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testUpdateCohort() throws Exception {
        Mockito.when(_learnerManagementService.updateCohort(Mockito.anyLong(), Mockito.any(Cohort.class)))
                .thenReturn(_cohort);
        Mockito.when(_learnerManagementService.toCohortResponseDTO(Mockito.any(Cohort.class)))
                .thenReturn(_cohortResponse);
        String cohortJson = JsonUtil.convertToJson(_cohort);
        mockMvc.perform(MockMvcRequestBuilders.put("/cohorts/{cohortId}",1L)
                        .contentType("application/json").content(cohortJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.cohortName").value(_cohortResponse.getCohortName()))
                .andExpect(jsonPath("$.cohortDescription").value(_cohortResponse.getCohortDescription()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testDeleteCohort() throws  Exception{
        Mockito.doNothing().when(_learnerManagementService).deleteCohort(Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/cohorts/{cohortId}",1L)
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(_learnerManagementService, Mockito.times(1)).deleteCohort(Mockito.anyLong());
    }

}
