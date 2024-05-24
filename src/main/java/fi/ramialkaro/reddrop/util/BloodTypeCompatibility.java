package fi.ramialkaro.reddrop.util;

import fi.ramialkaro.reddrop.model.enums.BloodType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BloodTypeCompatibility {

    private static final Map<BloodType, Set<BloodType>> compatibilityMap = new HashMap<>();

    static {
        compatibilityMap.put(BloodType.A_POSITIVE, new HashSet<>(
                Set.of(BloodType.A_POSITIVE, BloodType.A_NEGATIVE, BloodType.O_POSITIVE, BloodType.O_NEGATIVE)));
        compatibilityMap.put(BloodType.A_NEGATIVE, new HashSet<>(Set.of(BloodType.A_NEGATIVE, BloodType.O_NEGATIVE)));
        compatibilityMap.put(BloodType.B_POSITIVE, new HashSet<>(
                Set.of(BloodType.B_POSITIVE, BloodType.B_NEGATIVE, BloodType.O_POSITIVE, BloodType.O_NEGATIVE)));
        compatibilityMap.put(BloodType.B_NEGATIVE, new HashSet<>(Set.of(BloodType.B_NEGATIVE, BloodType.O_NEGATIVE)));
        compatibilityMap.put(BloodType.AB_POSITIVE,
                new HashSet<>(Set.of(BloodType.A_POSITIVE, BloodType.A_NEGATIVE, BloodType.B_POSITIVE,
                        BloodType.B_NEGATIVE, BloodType.AB_POSITIVE, BloodType.AB_NEGATIVE, BloodType.O_POSITIVE,
                        BloodType.O_NEGATIVE)));
        compatibilityMap.put(BloodType.AB_NEGATIVE, new HashSet<>(
                Set.of(BloodType.A_NEGATIVE, BloodType.B_NEGATIVE, BloodType.AB_NEGATIVE, BloodType.O_NEGATIVE)));
        compatibilityMap.put(BloodType.O_POSITIVE, new HashSet<>(Set.of(BloodType.O_POSITIVE, BloodType.O_NEGATIVE)));
        compatibilityMap.put(BloodType.O_NEGATIVE, new HashSet<>(Set.of(BloodType.O_NEGATIVE)));
    }

    public static boolean isCompatible(BloodType donorBloodType, BloodType receiverBloodType) {
        return compatibilityMap.getOrDefault(receiverBloodType, new HashSet<>()).contains(donorBloodType);
    }
}
