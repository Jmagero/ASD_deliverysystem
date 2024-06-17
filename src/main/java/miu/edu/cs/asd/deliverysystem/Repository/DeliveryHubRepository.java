package miu.edu.cs.asd.deliverysystem.Repository;

import miu.edu.cs.asd.deliverysystem.model.DeliveryHub;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryHubRepository extends JpaRepository<DeliveryHub, Long> {
    Optional<DeliveryHub> findByHubName(String name);
}
