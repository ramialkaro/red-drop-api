package fi.ramialkaro.reddrop.controller;

import fi.ramialkaro.reddrop.model.Receiver;
import fi.ramialkaro.reddrop.service.ReceiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/receivers")
public class ReceiverController {

    @Autowired
    private ReceiverService receiverService;

    @GetMapping
    public List<Receiver> getAllReceivers() {
        return receiverService.getAllReceivers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Receiver> getReceiverById(@PathVariable Long id) {
        return receiverService.getReceiverById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /*
     * @PostMapping
     * public ResponseEntity<Receiver> createReceiver(@RequestBody Receiver
     * receiver) {
     * Receiver createdReceiver = receiverService.createReceiver(receiver);
     * return new ResponseEntity<>(createdReceiver, HttpStatus.CREATED);
     * }
     */

    /*
     * @DeleteMapping("/{id}")
     * public ResponseEntity<Void> deleteReceiver(@PathVariable Long id) {
     * receiverService.deleteReceiver(id);
     * return ResponseEntity.noContent().build();
     * }
     */
}
