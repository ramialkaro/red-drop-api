package fi.ramialkaro.reddrop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fi.ramialkaro.reddrop.model.Donation;
import fi.ramialkaro.reddrop.service.DonationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fi.ramialkaro.reddrop.model.Donor;
import fi.ramialkaro.reddrop.model.Receiver;
import fi.ramialkaro.reddrop.model.Organization;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class DonationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DonationService donationService;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Donation donation1 = new Donation();
        donation1.setId(1L);
        donation1.setDonor(createMockDonor());
        donation1.setReceiver(createMockReceiver());
        donation1.setDonationDate(LocalDateTime.now());
        donation1.setCreatedAt(LocalDateTime.now());
        donation1.setUpdatedAt(LocalDateTime.now());
        donation1.setOrganization(createMockOrganization());

        Donation donation2 = new Donation();
        donation2.setId(2L);
        donation2.setDonor(createMockDonor());
        donation2.setReceiver(createMockReceiver());
        donation2.setDonationDate(LocalDateTime.now());
        donation2.setCreatedAt(LocalDateTime.now());
        donation2.setUpdatedAt(LocalDateTime.now());
        donation2.setOrganization(createMockOrganization());

        List<Donation> donations = Arrays.asList(donation1, donation2);

        when(donationService.getAllDonations()).thenReturn(donations);
        when(donationService.getDonationById(1L)).thenReturn(Optional.of(donation1));
        when(donationService.getDonationById(2L)).thenReturn(Optional.of(donation2));
    }

    private Organization createMockOrganization() {
        Organization organization = new Organization();
        organization.setId(1L);
        organization.setEmail("test@test.fi");
        return organization;
    }

    private Receiver createMockReceiver() {
        Receiver receiver = new Receiver();
        receiver.setId(1L);
        return receiver;
    }

    private Donor createMockDonor() {
        Donor donor = new Donor();
        donor.setId(1L);
        return donor;
    }

    @Test
    public void getAllDonationsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/donations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetDonationById_DonationExists() throws Exception {
        Donation donation = new Donation();
        donation.setId(1L);
        donation.setDonor(createMockDonor());
        donation.setReceiver(createMockReceiver());
        donation.setDonationDate(LocalDateTime.now());
        donation.setCreatedAt(LocalDateTime.now());
        donation.setUpdatedAt(LocalDateTime.now());
        donation.setOrganization(createMockOrganization());

        when(donationService.getDonationById(1L)).thenReturn(Optional.of(donation));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/donations/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetDonationById_DonationDoesNotExist() throws Exception {
        when(donationService.getDonationById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/donations/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddDonation() throws Exception {
        Donation donation = new Donation();
        donation.setId(1L);
        donation.setDonor(createMockDonor());
        donation.setReceiver(createMockReceiver());
        donation.setDonationDate(LocalDateTime.now());
        donation.setCreatedAt(LocalDateTime.now());
        donation.setUpdatedAt(LocalDateTime.now());
        donation.setOrganization(createMockOrganization());

        when(donationService.addDonation(donation)).thenReturn(donation);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/donations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(donation)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void testDeleteDonation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/donations/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetDonationsGroupedByDonorId() throws Exception {
        List<Map<String, Object>> groupedDonations = Arrays.asList(Map.of("donorId",
                1L, "count", 2));
        when(donationService.getDonationsGroupedByDonorId()).thenReturn(groupedDonations);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/donations/groupedByDonor")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].donorId").value(1L))
                .andExpect(jsonPath("$[0].count").value(2));
    }
}
