package fi.ramialkaro.reddrop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

public class DonationTest {

    @Test
    public void testToString() {
        Donation donation = new Donation();
        donation.setId(1L);
        donation.setDonationDate(LocalDateTime.of(2024, 5, 30, 15, 30));

        String expected = "Donation [id=1, donor=null, receiver=null, donationDate=2024-05-30T15:30, createdAt=null, updatedAt=null, organization=null]";

        assertEquals(expected, donation.toString());
    }
}
