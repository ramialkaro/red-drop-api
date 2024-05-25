
package fi.ramialkaro.reddrop.service;

import fi.ramialkaro.reddrop.model.Donation;
import fi.ramialkaro.reddrop.model.Donor;
import fi.ramialkaro.reddrop.model.Receiver;
import fi.ramialkaro.reddrop.model.enums.BloodType;
import fi.ramialkaro.reddrop.repository.DonationRepository;
import fi.ramialkaro.reddrop.repository.DonorRepository;
import fi.ramialkaro.reddrop.repository.ReceiverRepository;
import fi.ramialkaro.reddrop.util.BloodTypeCompatibility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DonationServiceTest {

    @InjectMocks
    private DonationService donationService;

    @Mock
    private DonationRepository donationRepository;

    @Mock
    private DonorRepository donorRepository;

    @Mock
    private ReceiverRepository receiverRepository;

    private Donor donor;
    private Receiver receiver;
    private Donation donation;

    @BeforeEach
    void setUp() {
        donor = new Donor();
        donor.setId(1L);
        donor.setBloodType(BloodType.A_POSITIVE);
        donor.setHasInfectionOrDisease(false);
        donor.setSmoker(false);
        donor.setConsumesAlcohol(false);

        receiver = new Receiver();
        receiver.setId(1L);
        receiver.setBloodType(BloodType.A_POSITIVE);
        receiver.setHasInfectionOrDisease(false);
        receiver.setSmoker(false);
        receiver.setConsumesAlcohol(false);

        donation = new Donation();
        donation.setId(1L);
        donation.setDonor(donor);
        donation.setReceiver(receiver);
    }

    @Test
    void testGetAllDonations() {
        List<Donation> donations = Arrays.asList(donation);

        when(donationRepository.findAll()).thenReturn(donations);

        List<Donation> result = donationService.getAllDonations();

        assertEquals(1, result.size());
        assertEquals(donations, result);

        verify(donationRepository, times(1)).findAll();
    }

    @Test
    void testGetAllDonations_ErrorHandling() {
        when(donationRepository.findAll()).thenThrow(new RuntimeException("Repository error"));

        List<Donation> result = donationService.getAllDonations();

        assertTrue(result.isEmpty());

        verify(donationRepository, times(1)).findAll();
    }

    @Test
    void testGetDonationById() {
        when(donationRepository.findById(1L)).thenReturn(Optional.of(donation));

        Optional<Donation> result = donationService.getDonationById(1L);

        assertTrue(result.isPresent());
        assertEquals(donation, result.get());
    }

    @Test
    void testGetDonationById_Success() {
        when(donationRepository.findById(1L)).thenReturn(Optional.of(donation));

        Optional<Donation> result = donationService.getDonationById(1L);

        assertTrue(result.isPresent());
        assertEquals(donation, result.get());
    }

    @Test
    void testGetDonationById_NotFound() {
        when(donationRepository.findById(1L)).thenThrow(new DonationNotFoundException("Donation with id 1 not found"));

        Optional<Donation> result = donationService.getDonationById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void testAddDonation_Success() {
        when(donorRepository.findById(1L)).thenReturn(Optional.of(donor));
        when(receiverRepository.findById(1L)).thenReturn(Optional.of(receiver));
        when(donationRepository.save(any(Donation.class))).thenReturn(donation);

        Donation result = donationService.addDonation(donation);

        assertEquals(donation, result);
    }

    @Test
    void testAddDonation_DonorNotFound() {
        when(donorRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            donationService.addDonation(donation);
        });

        assertEquals("Donor does not exist", exception.getMessage());
    }

    @Test
    void testAddDonation_ReceiverNotFound() {
        when(donorRepository.findById(1L)).thenReturn(Optional.of(donor));
        when(receiverRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            donationService.addDonation(donation);
        });

        assertEquals("Receiver does not exist", exception.getMessage());
    }

    @Test
    void testAddDonation_ReceiverNotNull() {
        when(donorRepository.findById(1L)).thenReturn(Optional.of(donor));
        when(receiverRepository.findById(1L)).thenReturn(Optional.of(receiver));
        when(donationRepository.save(any(Donation.class))).thenReturn(donation);

        Donation result = donationService.addDonation(donation);

        assertNotNull(result.getReceiver());
        assertEquals(donation, result);
    }

    @Test
    void testAddDonation_NoReceiver() {
        donation.setReceiver(null);
        when(donorRepository.findById(1L)).thenReturn(Optional.of(donor));
        when(donationRepository.save(any(Donation.class))).thenReturn(donation);

        Donation result = donationService.addDonation(donation);

        assertEquals(donation, result);
        assertNull(result.getReceiver());
    }

    @Test
    void testAddDonation_IncompatibleBloodTypes() {
        donor.setBloodType(BloodType.A_POSITIVE);
        receiver.setBloodType(BloodType.B_POSITIVE);
        when(donorRepository.findById(1L)).thenReturn(Optional.of(donor));
        when(receiverRepository.findById(1L)).thenReturn(Optional.of(receiver));

        try (MockedStatic<BloodTypeCompatibility> mockedStatic = mockStatic(BloodTypeCompatibility.class)) {
            mockedStatic.when(() -> BloodTypeCompatibility.isCompatible(BloodType.A_POSITIVE, BloodType.B_POSITIVE))
                    .thenReturn(false);

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                donationService.addDonation(donation);
            });

            assertEquals(
                    "Incompatible blood types: " + BloodType.A_POSITIVE + " cannot donate to " + BloodType.B_POSITIVE,
                    exception.getMessage());
        }
    }

    @Test
    void testAddDonation_InfectionOrDisease() {
        donor.setHasInfectionOrDisease(true);
        when(donorRepository.findById(1L)).thenReturn(Optional.of(donor));
        when(receiverRepository.findById(1L)).thenReturn(Optional.of(receiver));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            donationService.addDonation(donation);
        });

        assertEquals("Donation cannot proceed due to infection or disease. Details: Donor: null, Receiver: null",
                exception.getMessage());
    }

    @Test
    void testAddDonation_SmokingHabits() {
        donor.setSmoker(true);
        when(donorRepository.findById(1L)).thenReturn(Optional.of(donor));
        when(receiverRepository.findById(1L)).thenReturn(Optional.of(receiver));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            donationService.addDonation(donation);
        });

        assertEquals("Donation cannot proceed due to smoking habits", exception.getMessage());
    }

    @Test
    void testAddDonation_AlcoholConsumption() {
        donor.setConsumesAlcohol(true);
        when(donorRepository.findById(1L)).thenReturn(Optional.of(donor));
        when(receiverRepository.findById(1L)).thenReturn(Optional.of(receiver));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            donationService.addDonation(donation);
        });

        assertEquals("Donation cannot proceed due to alcohol consumption", exception.getMessage());
    }

    @Test
    void testDeleteDonation_Success() {
        doNothing().when(donationRepository).deleteById(1L);

        donationService.deleteDonation(1L);

        verify(donationRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteDonation_ExceptionHandling() {
        doThrow(new RuntimeException("Deletion error")).when(donationRepository).deleteById(1L);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(outContent));

        donationService.deleteDonation(1L);

        verify(donationRepository, times(1)).deleteById(1L);

        assertTrue(outContent.toString().contains("Error deleting donation with id 1: Deletion error"));
    }

    @Test
    void testGetDonationsGroupedByDonorId() {
        List<Map<String, Object>> groupedDonations = Arrays.asList(Map.of("donorId", 1L, "count", 2));
        when(donationRepository.findDonationsGroupedByDonorId()).thenReturn(groupedDonations);

        List<Map<String, Object>> result = donationService.getDonationsGroupedByDonorId();

        assertEquals(1, result.size());
        assertEquals(groupedDonations, result);
    }

    @Test
    void testAddDonation_ReceiverHasInfectionOrDisease() {
        receiver.setHasInfectionOrDisease(true);
        when(donorRepository.findById(1L)).thenReturn(Optional.of(donor));
        when(receiverRepository.findById(1L)).thenReturn(Optional.of(receiver));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            donationService.addDonation(donation);
        });

        assertEquals(
                "Donation cannot proceed due to infection or disease. Details: Donor: null, Receiver: null",
                exception.getMessage());
    }

    @Test
    void testAddDonation_ReceiverIsSmoker() {
        receiver.setSmoker(true);
        when(donorRepository.findById(1L)).thenReturn(Optional.of(donor));
        when(receiverRepository.findById(1L)).thenReturn(Optional.of(receiver));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            donationService.addDonation(donation);
        });

        assertEquals("Donation cannot proceed due to smoking habits", exception.getMessage());
    }

    @Test
    void testAddDonation_ReceiverConsumesAlcohol() {
        receiver.setConsumesAlcohol(true);
        when(donorRepository.findById(1L)).thenReturn(Optional.of(donor));
        when(receiverRepository.findById(1L)).thenReturn(Optional.of(receiver));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            donationService.addDonation(donation);
        });

        assertEquals("Donation cannot proceed due to alcohol consumption", exception.getMessage());
    }

}
