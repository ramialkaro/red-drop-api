package fi.ramialkaro.reddrop.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fi.ramialkaro.reddrop.model.Donation;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query("SELECT d.donor.id as donorId, COUNT(d) as donationCount FROM Donation d GROUP BY d.donor.id")
    List<Map<String, Object>> findDonationsGroupedByDonorId();
}