package fi.ramialkaro.reddrop.controller;

import fi.ramialkaro.reddrop.model.enums.BloodType;
import fi.ramialkaro.reddrop.service.BloodTypeCompatibilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/compatibility")

public class BloodTypeCompatibilityController {

    @Autowired
    private BloodTypeCompatibilityService compatibilityService;

    @GetMapping
    public CompatibilityResponse checkCompatibility(
            @RequestParam BloodType donorBloodType,
            @RequestParam BloodType receiverBloodType) {
        boolean isCompatible = compatibilityService.isCompatible(donorBloodType, receiverBloodType);
        return new CompatibilityResponse(donorBloodType, receiverBloodType, isCompatible);
    }

    public static class CompatibilityResponse {
        private BloodType donorBloodType;
        private BloodType receiverBloodType;
        private boolean compatible;

        public CompatibilityResponse(BloodType donorBloodType, BloodType receiverBloodType, boolean compatible) {
            this.donorBloodType = donorBloodType;
            this.receiverBloodType = receiverBloodType;
            this.compatible = compatible;
        }

        public BloodType getDonorBloodType() {
            return donorBloodType;
        }

        public BloodType getReceiverBloodType() {
            return receiverBloodType;
        }

        public boolean isCompatible() {
            return compatible;
        }
    }
}
