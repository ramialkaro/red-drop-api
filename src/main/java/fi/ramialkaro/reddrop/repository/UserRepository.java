package fi.ramialkaro.reddrop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.ramialkaro.reddrop.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}