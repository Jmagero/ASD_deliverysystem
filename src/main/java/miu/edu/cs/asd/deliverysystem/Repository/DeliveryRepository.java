package miu.edu.cs.asd.deliverysystem.Repository;

import miu.edu.cs.asd.deliverysystem.model.Customer;
import miu.edu.cs.asd.deliverysystem.model.Delivery;
import miu.edu.cs.asd.deliverysystem.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findAllByDriver(Driver driver);
    List<Delivery>findAllByCustomer(Customer customer);
}
