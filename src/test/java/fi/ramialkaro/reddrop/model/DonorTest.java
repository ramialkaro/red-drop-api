package fi.ramialkaro.reddrop.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import fi.ramialkaro.reddrop.model.enums.BloodType;

public class DonorTest {

    @Test
    public void testToString() {
        Donor donor = new Donor();
        donor.setId(1L);
        donor.setBloodType(BloodType.A_NEGATIVE);
        donor.setHasInfectionOrDisease(false);
        donor.setDiseaseDescription(null);
        donor.setSmoker(true);
        donor.setConsumesAlcohol(false);

        String expected = "Donor [id=1, bloodType=A_NEGATIVE, user=null, organization=null, hasInfectionOrDisease=false, diseaseDescription=null, isSmoker=true, consumesAlcohol=false]";

        assertEquals(expected, donor.toString());
    }
}