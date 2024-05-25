package fi.ramialkaro.reddrop.controller;

import fi.ramialkaro.reddrop.model.Receiver;
import fi.ramialkaro.reddrop.service.ReceiverService;
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
public class ReceiverControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReceiverService receiverService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Receiver receiver1 = createMockReceiver(1L);
        Receiver receiver2 = createMockReceiver(2L);

        List<Receiver> receivers = Arrays.asList(receiver1, receiver2);

        when(receiverService.getAllReceivers()).thenReturn(receivers);
        when(receiverService.getReceiverById(1L)).thenReturn(Optional.of(receiver1));
        when(receiverService.getReceiverById(2L)).thenReturn(Optional.of(receiver2));
    }

    private Receiver createMockReceiver(Long id) {
        Receiver receiver = new Receiver();
        receiver.setId(id);
        return receiver;
    }

    @Test
    public void getAllReceiversTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/receivers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    @Test
    void testGetReceiverById_ReceiverExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/receivers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetReceiverById_ReceiverDoesNotExist() throws Exception {
        when(receiverService.getReceiverById(3L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/receivers/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
