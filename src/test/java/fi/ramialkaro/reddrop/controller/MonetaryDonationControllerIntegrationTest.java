package fi.ramialkaro.reddrop.controller;

import fi.ramialkaro.reddrop.model.Donor;
import fi.ramialkaro.reddrop.model.MonetaryDonation;
import fi.ramialkaro.reddrop.model.User;
import fi.ramialkaro.reddrop.service.MonetaryDonationService;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MonetaryDonationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MonetaryDonationService monetaryDonationService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private MonetaryDonation createMockMonetaryDonation(Long id, String donorName, double amount) {
        MonetaryDonation donation = new MonetaryDonation();
        Donor donor = new Donor();
        User user = new User();

        user.setFirstName("Jane Doe");
        donor.setUser(user);
        donation.setId(id);
        donation.setDonor(donor);
        donation.setAmount(amount);
        return donation;
    }

    @Test
    void testGetAllMonetaryDonations() throws Exception {
        MonetaryDonation donation1 = createMockMonetaryDonation(1L, "John Doe", 100.0);
        MonetaryDonation donation2 = createMockMonetaryDonation(2L, "Jane Doe", 150.0);
        when(monetaryDonationService.getAllMonetaryDonations()).thenReturn(Arrays.asList(donation1, donation2));

        mockMvc.perform(MockMvcRequestBuilders.get("/monetary-donations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].amount").value(100.0))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].donor.user.firstName").value("Jane Doe"))
                .andExpect(jsonPath("$[1].amount").value(150.0));
    }

    @Test
    void testGetMonetaryDonationById() throws Exception {
        MonetaryDonation donation = createMockMonetaryDonation(1L, "John Doe", 100.0);
        when(monetaryDonationService.getMonetaryDonationById(1L)).thenReturn(Optional.of(donation));

        mockMvc.perform(MockMvcRequestBuilders.get("/monetary-donations/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.donor.user.firstName").value("Jane Doe"))
                .andExpect(jsonPath("$.amount").value(100.0));
    }

    @Test
    void testCreateMonetaryDonation() throws Exception {
        MonetaryDonation donation = createMockMonetaryDonation(1L, "John Doe", 100.0);
        when(monetaryDonationService.createMonetaryDonation(donation)).thenReturn(donation);

        mockMvc.perform(MockMvcRequestBuilders.post("/monetary-donations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(donation)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteMonetaryDonation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/monetary-donations/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
