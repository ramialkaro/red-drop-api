package fi.ramialkaro.reddrop.controller;

import fi.ramialkaro.reddrop.model.Donor;
import fi.ramialkaro.reddrop.service.DonorService;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DonorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DonorService donorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Donor donor1 = createMockDonor(1L);
        Donor donor2 = createMockDonor(2L);

        List<Donor> donors = Arrays.asList(donor1, donor2);

        when(donorService.getAllDonors()).thenReturn(donors);
        when(donorService.getDonorById(1L)).thenReturn(Optional.of(donor1));
        when(donorService.getDonorById(2L)).thenReturn(Optional.of(donor2));
    }

    private Donor createMockDonor(Long id) {
        Donor donor = new Donor();
        donor.setId(id);
        return donor;
    }

    @Test
    public void getAllDonorsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/donors")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    @Test
    void testGetDonorById_DonorExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/donors/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetDonorById_DonorDoesNotExist() throws Exception {
        when(donorService.getDonorById(3L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/donors/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
