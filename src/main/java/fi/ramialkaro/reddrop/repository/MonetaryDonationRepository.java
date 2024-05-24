package fi.ramialkaro.reddrop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fi.ramialkaro.reddrop.model.MonetaryDonation;

@Repository
public interface MonetaryDonationRepository extends JpaRepository<MonetaryDonation, Long> {
}