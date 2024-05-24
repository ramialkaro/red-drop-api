package fi.ramialkaro.reddrop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fi.ramialkaro.reddrop.model.Receiver;

@Repository
public interface ReceiverRepository extends JpaRepository<Receiver, Long> {
}