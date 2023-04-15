package ro.nicolaemariusghergu.loginjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.nicolaemariusghergu.loginjwt.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
}
