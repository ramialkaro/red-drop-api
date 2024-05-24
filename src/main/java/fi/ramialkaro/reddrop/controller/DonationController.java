package fi.ramialkaro.reddrop.controller;

import fi.ramialkaro.reddrop.model.Donation;
import fi.ramialkaro.reddrop.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/donations")
public class DonationController {

    @Autowired
    private DonationService donationService;

    @GetMapping
    public List<Donation> getAllDonations() {
        return donationService.getAllDonations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donation> getDonationById(@PathVariable Long id) {
        Optional<Donation> donation = donationService.getDonationById(id);
        return donation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Donation> addDonation(@RequestBody Donation donation) {
        try {
            Donation createdDonation = donationService.addDonation(donation);
            return ResponseEntity.ok(createdDonation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonation(@PathVariable Long id) {
        donationService.deleteDonation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/groupedByDonor")
    public List<Map<String, Object>> getDonationsGroupedByDonorId() {
        return donationService.getDonationsGroupedByDonorId();
    }
}
