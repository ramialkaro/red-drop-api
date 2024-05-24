package fi.ramialkaro.reddrop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import fi.ramialkaro.reddrop.model.Organization;
import fi.ramialkaro.reddrop.model.Receiver;
import fi.ramialkaro.reddrop.model.User;
import fi.ramialkaro.reddrop.repository.OrganizationRepository;
import fi.ramialkaro.reddrop.repository.ReceiverRepository;
import fi.ramialkaro.reddrop.repository.UserRepository;

class ReceiverServiceTest {

    @InjectMocks
    private ReceiverService receiverService;

    @Mock
    private ReceiverRepository receiverRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrganizationRepository organizationRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllReceivers() {
        Receiver receiver1 = new Receiver();
        Receiver receiver2 = new Receiver();
        List<Receiver> receivers = Arrays.asList(receiver1, receiver2);

        when(receiverRepository.findAll()).thenReturn(receivers);

        List<Receiver> result = receiverService.getAllReceivers();

        assertEquals(2, result.size());
        assertEquals(receivers, result);
    }

    @Test
    void testGetReceiverById() {
        Receiver receiver = new Receiver();
        receiver.setId(1L);

        when(receiverRepository.findById(1L)).thenReturn(Optional.of(receiver));

        Optional<Receiver> result = receiverService.getReceiverById(1L);

        assertEquals(true, result.isPresent());
        assertEquals(receiver, result.get());
    }

    @Test
    void testCreateReceiver() {
        User user = new User();
        user.setId(1L);

        Organization organization = new Organization();
        organization.setId(1L);

        Receiver receiver = new Receiver();
        receiver.setUser(user);
        receiver.setOrganization(organization);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(organizationRepository.findById(1L)).thenReturn(Optional.of(organization));
        when(receiverRepository.save(any(Receiver.class))).thenReturn(receiver);

        Receiver result = receiverService.createReceiver(receiver);

        assertEquals(receiver, result);
    }

    @Test
    void testCreateReceiver_UserNotFound() {
        Receiver receiver = new Receiver();
        User user = new User();
        user.setId(1L);
        receiver.setUser(user);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            receiverService.createReceiver(receiver);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testCreateReceiver_OrganizationNotFound() {
        User user = new User();
        user.setId(1L);
        Receiver receiver = new Receiver();
        receiver.setUser(user);
        Organization organization = new Organization();
        organization.setId(1L);
        receiver.setOrganization(organization);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(organizationRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            receiverService.createReceiver(receiver);
        });

        assertEquals("Organization not found", exception.getMessage());
    }

    @Test
    void testDeleteReceiver() {
        receiverService.deleteReceiver(1L);
        verify(receiverRepository).deleteById(1L);
    }
}
