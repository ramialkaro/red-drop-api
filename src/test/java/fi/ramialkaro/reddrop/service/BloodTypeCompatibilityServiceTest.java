package fi.ramialkaro.reddrop.service;

import fi.ramialkaro.reddrop.model.enums.BloodType;
import fi.ramialkaro.reddrop.util.BloodTypeCompatibility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class BloodTypeCompatibilityServiceTest {

    private BloodTypeCompatibilityService bloodTypeCompatibilityService;

    @BeforeEach
    void setUp() {
        bloodTypeCompatibilityService = new BloodTypeCompatibilityService();
    }

    @Test
    void testIsCompatible() {
        try (MockedStatic<BloodTypeCompatibility> mockedStatic = mockStatic(BloodTypeCompatibility.class)) {
            mockedStatic.when(() -> BloodTypeCompatibility.isCompatible(BloodType.A_POSITIVE, BloodType.A_POSITIVE))
                    .thenReturn(true);
            mockedStatic.when(() -> BloodTypeCompatibility.isCompatible(BloodType.A_POSITIVE, BloodType.B_POSITIVE))
                    .thenReturn(false);

            boolean result1 = bloodTypeCompatibilityService.isCompatible(BloodType.A_POSITIVE, BloodType.A_POSITIVE);
            boolean result2 = bloodTypeCompatibilityService.isCompatible(BloodType.A_POSITIVE, BloodType.B_POSITIVE);

            assertTrue(result1, BloodType.A_POSITIVE + " should be compatible with " + BloodType.A_POSITIVE);
            assertFalse(result2, BloodType.A_POSITIVE + " should not be compatible with " + BloodType.B_POSITIVE);

        }
    }
}
