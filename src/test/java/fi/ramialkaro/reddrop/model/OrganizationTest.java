package fi.ramialkaro.reddrop.model;

import org.junit.jupiter.api.Test;

import fi.ramialkaro.reddrop.model.enums.OrganizationType;
import fi.ramialkaro.reddrop.model.enums.ServiceType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrganizationTest {

    @Test
    public void testToString() {
        Organization organization = new Organization();
        organization.setId(1L);
        organization.setName("Test Organization");
        organization.setAddress("123 Main St");
        organization.setContactNumber("123-456-7890");
        organization.setEmail("test@example.com");
        organization.setType(OrganizationType.HOSPITAL);
        organization.setServicesProvided(ServiceType.BLOOD_DONATION);

        String expected = "Organization [id=1, name=Test Organization, address=123 Main St, contactNumber=123-456-7890, email=test@example.com, type=HOSPITAL, servicesProvided=BLOOD_DONATION, createdAt=null, updatedAt=null, donors=null, receivers=null, donations=null, monetaryDonations=null]";

        assertEquals(expected, organization.toString());
    }
}
