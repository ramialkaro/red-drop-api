package fi.ramialkaro.reddrop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    @Test
    public void testToString() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setPassword("password123");
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");

        String expected = "User [id=1, username=testUser, password=password123, email=test@example.com, firstName=John, lastName=Doe, donor=null, receiver=null, createdAt=null, updatedAt=null]";

        assertEquals(expected, user.toString());
    }
}
