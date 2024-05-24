package fi.ramialkaro.reddrop.service;

import fi.ramialkaro.reddrop.model.enums.BloodType;
import fi.ramialkaro.reddrop.util.BloodTypeCompatibility;
import org.springframework.stereotype.Service;

@Service
public class BloodTypeCompatibilityService {

    public boolean isCompatible(BloodType donorBloodType, BloodType receiverBloodType) {
        return BloodTypeCompatibility.isCompatible(donorBloodType, receiverBloodType);
    }
}
