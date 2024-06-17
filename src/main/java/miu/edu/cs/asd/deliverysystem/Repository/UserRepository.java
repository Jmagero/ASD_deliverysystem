package miu.edu.cs.asd.deliverysystem.Repository;

import miu.edu.cs.asd.deliverysystem.dto.customer.CustomerResponseDto;
import miu.edu.cs.asd.deliverysystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

}
