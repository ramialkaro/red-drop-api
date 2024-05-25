package fi.ramialkaro.reddrop.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import fi.ramialkaro.reddrop.model.enums.BloodType;

public class ReceiverTest {
    @Test
    public void testToString() {
        Receiver receiver = new Receiver();
        receiver.setId(1L);
        receiver.setBloodType(BloodType.B_POSITIVE);
        receiver.setHasInfectionOrDisease(true);
        receiver.setDiseaseDescription("Chronic illness");
        receiver.setSmoker(false);
        receiver.setConsumesAlcohol(true);

        String expected = "Receiver [id=1, user=null, bloodType=B_POSITIVE, organization=null, hasInfectionOrDisease=true, diseaseDescription=Chronic illness, isSmoker=false, consumesAlcohol=true]";

        assertEquals(expected, receiver.toString());
    }
}