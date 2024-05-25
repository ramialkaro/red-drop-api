package fi.ramialkaro.reddrop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

public class MonetaryDonationTest {

    @Test
    public void testToString() {
        MonetaryDonation donation = new MonetaryDonation();
        donation.setId(1L);
        donation.setAmount(100.0);
        donation.setDonationDate(LocalDateTime.of(2024, 5, 30, 15, 30));

        String expected = "MonetaryDonation [id=1, donor=null, amount=100.0, donationDate=2024-05-30T15:30, createdAt=null, updatedAt=null, organization=null]";

        assertEquals(expected, donation.toString());
    }
}
