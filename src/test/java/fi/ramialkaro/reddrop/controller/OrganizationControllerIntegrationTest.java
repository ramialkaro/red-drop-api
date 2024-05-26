package fi.ramialkaro.reddrop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fi.ramialkaro.reddrop.model.Organization;
import fi.ramialkaro.reddrop.model.enums.OrganizationType;
import fi.ramialkaro.reddrop.model.enums.ServiceType;
import fi.ramialkaro.reddrop.service.OrganizationService;
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
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrganizationControllerIntegrationTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private OrganizationService organizationService;

        @Autowired
        private ObjectMapper objectMapper;

        @BeforeEach
        void setUp() {
                MockitoAnnotations.openMocks(this);
        }

        private Organization createMockOrganization(Long id, String name, String address, String contactNumber,
                        String email, String type, ServiceType servicesProvided) {
                Organization organization = new Organization();
                organization.setId(id);
                organization.setName(name);
                organization.setAddress(address);
                organization.setContactNumber(contactNumber);
                organization.setEmail(email);
                organization.setType(OrganizationType.CLINIC);
                organization.setServicesProvided(servicesProvided);
                return organization;
        }

        @Test
        void testGetAllOrganizations() throws Exception {
                Organization organization1 = createMockOrganization(1L, "Org1", "Address1", "Contact1", "Email1",
                                "Type1",
                                ServiceType.BLOOD_DONATION);
                Organization organization2 = createMockOrganization(2L, "Org2", "Address2", "Contact2", "Email2",
                                "Type2",
                                ServiceType.BLOOD_DONATION);
                when(organizationService.getAllOrganizations()).thenReturn(Arrays.asList(organization1, organization2));

                mockMvc.perform(MockMvcRequestBuilders.get("/organizations")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].id").value(1L))
                                .andExpect(jsonPath("$[0].name").value("Org1"))
                                .andExpect(jsonPath("$[1].id").value(2L))
                                .andExpect(jsonPath("$[1].name").value("Org2"));
        }

        @Test
        void testGetOrganizationById() throws Exception {
                Organization organization = createMockOrganization(1L, "Org1", "Address1", "Contact1", "Email1",
                                "Type1",
                                ServiceType.BLOOD_DONATION);
                when(organizationService.getOrganizationById(1L)).thenReturn(Optional.of(organization));

                mockMvc.perform(MockMvcRequestBuilders.get("/organizations/1")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(1L))
                                .andExpect(jsonPath("$.name").value("Org1"))
                                .andExpect(jsonPath("$.address").value("Address1"));
        }

        @Test
        void testCreateOrganization() throws Exception {
                Organization organization = createMockOrganization(1L, "Org1", "Address1", "Contact1", "Email1",
                                "Type1",
                                ServiceType.BLOOD_DONATION);
                when(organizationService.createOrganization(organization)).thenReturn(organization);

                mockMvc.perform(MockMvcRequestBuilders.post("/organizations")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(organization)))
                                .andExpect(status().isOk());
        }

        @Test
        void testUpdateOrganization() throws Exception {
                Organization existingOrganization = createMockOrganization(1L, "Org1", "Address1", "Contact1", "Email1",
                                "Type1", ServiceType.BLOOD_DONATION);
                Organization updatedOrganization = createMockOrganization(1L, "UpdatedOrg", "UpdatedAddress",
                                "UpdatedContact",
                                "UpdatedEmail", "UpdatedType", ServiceType.CONSULTATION);

                when(organizationService.getOrganizationById(1L)).thenReturn(Optional.of(existingOrganization));
                when(organizationService.createOrganization(existingOrganization)).thenReturn(updatedOrganization);

                mockMvc.perform(MockMvcRequestBuilders.put("/organizations/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updatedOrganization)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(1L))
                                .andExpect(jsonPath("$.name").value("UpdatedOrg"))
                                .andExpect(jsonPath("$.address").value("UpdatedAddress"))
                                .andExpect(jsonPath("$.contactNumber").value("UpdatedContact"))
                                .andExpect(jsonPath("$.email").value("UpdatedEmail"))
                                .andExpect(jsonPath("$.type").value(OrganizationType.CLINIC.toString()))
                                .andExpect(jsonPath("$.servicesProvided").value(ServiceType.CONSULTATION.toString()));
        }

        @Test
        void testDeleteOrganization() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.delete("/organizations/1")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNoContent());
        }

        @Test
        void testGetOrganizationByIdNotFound() throws Exception {
                when(organizationService.getOrganizationById(1L)).thenReturn(Optional.empty());

                mockMvc.perform(MockMvcRequestBuilders.get("/organizations/1")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound());
        }

}
