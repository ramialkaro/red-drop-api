package fi.ramialkaro.reddrop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fi.ramialkaro.reddrop.model.Donor;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {
}