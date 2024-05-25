package fi.ramialkaro.reddrop.controller;

import fi.ramialkaro.reddrop.model.enums.BloodType;
import fi.ramialkaro.reddrop.service.BloodTypeCompatibilityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BloodTypeCompatibilityControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BloodTypeCompatibilityService compatibilityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCheckCompatibility() throws Exception {
        BloodType donorBloodType = BloodType.O_POSITIVE;
        BloodType receiverBloodType = BloodType.A_POSITIVE;
        boolean isCompatible = true;

        when(compatibilityService.isCompatible(donorBloodType, receiverBloodType)).thenReturn(isCompatible);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/compatibility")
                .param("donorBloodType", donorBloodType.name())
                .param("receiverBloodType", receiverBloodType.name())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.donorBloodType").value(donorBloodType.name()))
                .andExpect(jsonPath("$.receiverBloodType").value(receiverBloodType.name()))
                .andExpect(jsonPath("$.compatible").value(isCompatible));
    }

    @Test
    void testCheckIncompatibility() throws Exception {
        BloodType donorBloodType = BloodType.A_NEGATIVE;
        BloodType receiverBloodType = BloodType.B_POSITIVE;
        boolean isCompatible = false;

        when(compatibilityService.isCompatible(donorBloodType, receiverBloodType)).thenReturn(isCompatible);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/compatibility")
                .param("donorBloodType", donorBloodType.name())
                .param("receiverBloodType", receiverBloodType.name())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.donorBloodType").value(donorBloodType.name()))
                .andExpect(jsonPath("$.receiverBloodType").value(receiverBloodType.name()))
                .andExpect(jsonPath("$.compatible").value(isCompatible));
    }
}
