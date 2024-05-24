package fi.ramialkaro.reddrop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fi.ramialkaro.reddrop.model.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}