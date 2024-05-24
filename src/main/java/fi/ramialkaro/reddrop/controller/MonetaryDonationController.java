package fi.ramialkaro.reddrop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.ramialkaro.reddrop.model.MonetaryDonation;
import fi.ramialkaro.reddrop.service.MonetaryDonationService;

@RestController
@RequestMapping("/api/monetary-donations")
public class MonetaryDonationController {

    @Autowired
    private MonetaryDonationService monetaryDonationService;

    @GetMapping
    public List<MonetaryDonation> getAllMonetaryDonations() {
        return monetaryDonationService.getAllMonetaryDonations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonetaryDonation> getMonetaryDonationById(@PathVariable Long id) {
        return monetaryDonationService.getMonetaryDonationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public MonetaryDonation createMonetaryDonation(@RequestBody MonetaryDonation monetaryDonation) {
        return monetaryDonationService.createMonetaryDonation(monetaryDonation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMonetaryDonation(@PathVariable Long id) {
        monetaryDonationService.deleteMonetaryDonation(id);
        return ResponseEntity.noContent().build();
    }
}