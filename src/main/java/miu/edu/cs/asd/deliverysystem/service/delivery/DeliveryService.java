package miu.edu.cs.asd.deliverysystem.service.delivery;

import miu.edu.cs.asd.deliverysystem.dto.customer.CustomerDeliveryResponseDto;
import miu.edu.cs.asd.deliverysystem.dto.customer.CustomerResponseDto;
import miu.edu.cs.asd.deliverysystem.dto.customer.DriverDeliveryResponse;
import miu.edu.cs.asd.deliverysystem.dto.delivery.DeliveryRequestDto;
import miu.edu.cs.asd.deliverysystem.dto.delivery.DeliveryResponseDto;

import java.util.List;
import java.util.Optional;

public interface DeliveryService {
    Optional<List<DriverDeliveryResponse>> findAllByDriver(String driverEmail);
    Optional<List<CustomerDeliveryResponseDto>> findAllByCustomer(String customerEmail);
    Optional<DeliveryResponseDto> updateDelivery(Long id, DeliveryRequestDto deliveryRequestDto);
    Optional<DeliveryResponseDto> createDelivery(DeliveryRequestDto deliveryRequestDto);
    Optional<DeliveryResponseDto> findById(Long id);
}
