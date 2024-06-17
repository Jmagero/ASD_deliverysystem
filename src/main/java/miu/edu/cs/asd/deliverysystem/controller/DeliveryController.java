package miu.edu.cs.asd.deliverysystem.controller;

import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import miu.edu.cs.asd.deliverysystem.dto.customer.CustomerDeliveryResponseDto;
import miu.edu.cs.asd.deliverysystem.dto.customer.DriverDeliveryResponse;
import miu.edu.cs.asd.deliverysystem.dto.delivery.DeliveryRequestDto;
import miu.edu.cs.asd.deliverysystem.dto.delivery.DeliveryResponseDto;
import miu.edu.cs.asd.deliverysystem.service.delivery.DeliveryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryResponseDto> getDelivery(@PathVariable Long id) {
        Optional<DeliveryResponseDto> delivery = deliveryService.findById(id);
        if (delivery.isPresent()) {
            return  ResponseEntity.ok(delivery.get());
        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<DeliveryResponseDto> createDelivery(@RequestBody @Valid DeliveryRequestDto deliveryRequestDto) {
        Optional<DeliveryResponseDto> delivery = deliveryService.createDelivery(deliveryRequestDto);
        if (delivery.isPresent()) {
            return  ResponseEntity.ok(delivery.get());
        }
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<DeliveryResponseDto> updateDelivery(@PathVariable Long id, @Valid @RequestBody DeliveryRequestDto deliveryRequestDto) {
        Optional<DeliveryResponseDto> delivery = deliveryService.updateDelivery(id, deliveryRequestDto);
        if (delivery.isPresent()) {
            return ResponseEntity.ok(delivery.get());
        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @GetMapping ("/customer/{customerEmail}")
    @PreAuthorize("hasAuthority('member:read')")
    public ResponseEntity<List<CustomerDeliveryResponseDto>> getAllDeliveriesByCustomer(@PathVariable String customerEmail) {
        Optional<List<CustomerDeliveryResponseDto>> deliveryResponseDtos = deliveryService.findAllByCustomer(customerEmail);
        if (deliveryResponseDtos.isPresent()) {
            return  ResponseEntity.ok(deliveryResponseDtos.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @GetMapping ("/driver/{driverEmail}")
    @PreAuthorize("hasAuthority('member:read')")
    public ResponseEntity<List<DriverDeliveryResponse>> getAllDeliveriesByDriver(@PathVariable String driverEmail) {
        Optional<List<DriverDeliveryResponse>> deliveryResponseDtos = deliveryService.findAllByDriver(driverEmail);
        if (deliveryResponseDtos.isPresent()) {
            return  ResponseEntity.ok(deliveryResponseDtos.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
