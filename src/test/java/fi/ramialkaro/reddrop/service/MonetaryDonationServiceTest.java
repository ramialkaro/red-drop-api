package fi.ramialkaro.reddrop.service;

import fi.ramialkaro.reddrop.model.Donor;
import fi.ramialkaro.reddrop.model.MonetaryDonation;
import fi.ramialkaro.reddrop.repository.MonetaryDonationRepository;
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
class MonetaryDonationServiceTest {

    @Mock
    private MonetaryDonationRepository monetaryDonationRepository;

    @InjectMocks
    private MonetaryDonationService monetaryDonationService;

    private MonetaryDonation monetaryDonation;

    @BeforeEach
    void setUp() {
        monetaryDonation = new MonetaryDonation();
        Donor donor = new Donor();

        monetaryDonation.setId(1L);
        monetaryDonation.setAmount(100.0);
        monetaryDonation.setDonor(donor);
    }

    @Test
    void testGetAllMonetaryDonations() {
        List<MonetaryDonation> monetaryDonations = Arrays.asList(monetaryDonation);
        when(monetaryDonationRepository.findAll()).thenReturn(monetaryDonations);

        List<MonetaryDonation> result = monetaryDonationService.getAllMonetaryDonations();

        assertEquals(1, result.size());
        assertEquals(100.0, result.get(0).getAmount());
        verify(monetaryDonationRepository, times(1)).findAll();
    }

    @Test
    void testGetMonetaryDonationById() {
        when(monetaryDonationRepository.findById(1L)).thenReturn(Optional.of(monetaryDonation));

        Optional<MonetaryDonation> result = monetaryDonationService.getMonetaryDonationById(1L);

        assertTrue(result.isPresent());
        assertEquals(100.0, result.get().getAmount());
        verify(monetaryDonationRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateMonetaryDonation() {
        when(monetaryDonationRepository.save(monetaryDonation)).thenReturn(monetaryDonation);

        MonetaryDonation result = monetaryDonationService.createMonetaryDonation(monetaryDonation);

        assertNotNull(result);
        assertEquals(100.0, result.getAmount());
        verify(monetaryDonationRepository, times(1)).save(monetaryDonation);
    }

    @Test
    void testDeleteMonetaryDonation() {
        doNothing().when(monetaryDonationRepository).deleteById(1L);

        monetaryDonationService.deleteMonetaryDonation(1L);

        verify(monetaryDonationRepository, times(1)).deleteById(1L);
    }
}
