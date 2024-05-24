package fi.ramialkaro.reddrop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import fi.ramialkaro.reddrop.model.Donor;
import fi.ramialkaro.reddrop.model.Organization;
import fi.ramialkaro.reddrop.model.User;
import fi.ramialkaro.reddrop.repository.DonorRepository;
import fi.ramialkaro.reddrop.repository.OrganizationRepository;
import fi.ramialkaro.reddrop.repository.UserRepository;

class DonorServiceTest {

    @InjectMocks
    private DonorService donorService;

    @Mock
    private DonorRepository donorRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrganizationRepository organizationRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDonors() {
        Donor donor1 = new Donor();
        Donor donor2 = new Donor();
        List<Donor> donors = Arrays.asList(donor1, donor2);

        when(donorRepository.findAll()).thenReturn(donors);

        List<Donor> result = donorService.getAllDonors();

        assertEquals(2, result.size());
        assertEquals(donors, result);
    }

    @Test
    void testGetDonorById() {
        Donor donor = new Donor();
        donor.setId(1L);

        when(donorRepository.findById(1L)).thenReturn(Optional.of(donor));

        Optional<Donor> result = donorService.getDonorById(1L);

        assertEquals(true, result.isPresent());
        assertEquals(donor, result.get());
    }

    @Test
    void testAddDonor() {
        User user = new User();
        user.setId(1L);

        Organization organization = new Organization();
        organization.setId(1L);

        Donor donor = new Donor();
        donor.setUser(user);
        donor.setOrganization(organization);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(organizationRepository.findById(1L)).thenReturn(Optional.of(organization));
        when(donorRepository.save(any(Donor.class))).thenReturn(donor);

        Donor result = donorService.addDonor(donor);

        assertEquals(donor, result);
    }

    @Test
    void testAddDonor_UserNotFound() {
        Donor donor = new Donor();
        User user = new User();
        user.setId(1L);
        donor.setUser(user);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            donorService.addDonor(donor);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testAddDonor_OrganizationNotFound() {
        User user = new User();
        user.setId(1L);
        Donor donor = new Donor();
        donor.setUser(user);
        Organization organization = new Organization();
        organization.setId(1L);
        donor.setOrganization(organization);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(organizationRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            donorService.addDonor(donor);
        });

        assertEquals("Organization not found", exception.getMessage());
    }

    @Test
    void testDeleteDonor() {
        donorService.deleteDonor(1L);
        Mockito.verify(donorRepository).deleteById(1L);
    }
}
