package miu.edu.cs.asd.deliverysystem.Repository;

import miu.edu.cs.asd.deliverysystem.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    Optional<Driver> findByEmail(String email);
}
