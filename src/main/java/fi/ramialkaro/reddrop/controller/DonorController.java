package fi.ramialkaro.reddrop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.ramialkaro.reddrop.model.Donor;
import fi.ramialkaro.reddrop.service.DonorService;

@RestController
@RequestMapping("/donors")
public class DonorController {
    @Autowired
    private DonorService donorService;

    @GetMapping
    public List<Donor> getAllDonors() {
        return donorService.getAllDonors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donor> getDonorById(@PathVariable Long id) {
        return donorService.getDonorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /*
     * @PostMapping
     * public ResponseEntity<Donor> createDonor(@RequestBody Donor donor) {
     * Donor createdDonor = donorService.addDonor(donor);
     * return ResponseEntity.ok(createdDonor);
     * }
     */

    /*
     * @DeleteMapping("/{id}")
     * public ResponseEntity<Void> deleteDonor(@PathVariable Long id) {
     * donorService.deleteDonor(id);
     * return ResponseEntity.noContent().build();
     * }
     */
}
