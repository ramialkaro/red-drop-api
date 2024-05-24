package fi.ramialkaro.reddrop.service;

import fi.ramialkaro.reddrop.model.Organization;
import fi.ramialkaro.reddrop.repository.OrganizationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrganizationServiceTest {

    @Mock
    private OrganizationRepository organizationRepository;

    @InjectMocks
    private OrganizationService organizationService;

    private Organization organization;

    @BeforeEach
    void setUp() {
        organization = new Organization();
        organization.setId(1L);
        organization.setName("Red Cross");
    }

    @Test
    void testGetAllOrganizations() {
        List<Organization> organizations = Arrays.asList(organization);
        when(organizationRepository.findAll()).thenReturn(organizations);

        List<Organization> result = organizationService.getAllOrganizations();

        assertEquals(1, result.size());
        assertEquals("Red Cross", result.get(0).getName());
        verify(organizationRepository, times(1)).findAll();
    }

    @Test
    void testGetOrganizationById() {
        when(organizationRepository.findById(1L)).thenReturn(Optional.of(organization));

        Optional<Organization> result = organizationService.getOrganizationById(1L);

        assertTrue(result.isPresent());
        assertEquals("Red Cross", result.get().getName());
        verify(organizationRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateOrganization() {
        when(organizationRepository.save(organization)).thenReturn(organization);

        Organization result = organizationService.createOrganization(organization);

        assertNotNull(result);
        assertEquals("Red Cross", result.getName());
        verify(organizationRepository, times(1)).save(organization);
    }

    @Test
    void testDeleteOrganization() {
        doNothing().when(organizationRepository).deleteById(1L);

        organizationService.deleteOrganization(1L);

        verify(organizationRepository, times(1)).deleteById(1L);
    }
}
