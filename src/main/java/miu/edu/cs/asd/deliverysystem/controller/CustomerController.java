package miu.edu.cs.asd.deliverysystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import miu.edu.cs.asd.deliverysystem.dto.customer.CustomerDeliveryResponseDto;
import miu.edu.cs.asd.deliverysystem.dto.customer.CustomerRequestDto;
import miu.edu.cs.asd.deliverysystem.dto.customer.CustomerResponseDto;
import miu.edu.cs.asd.deliverysystem.service.customer.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<CustomerResponseDto> addCustomer(@Valid  @RequestBody CustomerRequestDto customer) {
        Optional<CustomerResponseDto> customerOptional = customerService.addCustomer(customer);
        if (customerOptional.isPresent()) {
            return ResponseEntity.ok(customerOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{name}")
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<CustomerResponseDto> getCustomer(@PathVariable String name) {
        Optional<CustomerResponseDto> customerResponseDto = customerService.findByEmail(name);
        if (customerResponseDto.isPresent()) {
            return ResponseEntity.ok(customerResponseDto.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PatchMapping("/{name}")
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@PathVariable String name, @Valid @RequestBody CustomerRequestDto customer) {
        Optional<CustomerResponseDto> customerResponseDto = customerService.updateCustomer(name, customer);
        if (customerResponseDto.isPresent()) {
            return ResponseEntity.ok(customerResponseDto.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{name}")
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<CustomerResponseDto> deleteCustomer(@PathVariable String name) {
        customerService.deleteCustomer(name);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/placeOrder/{email}/{expectedDate}")
    @PreAuthorize("hasAuthority('member:read')")
    public ResponseEntity<CustomerDeliveryResponseDto> placeOrder(@PathVariable String email, @PathVariable String expectedDate) {
        Optional<CustomerDeliveryResponseDto> customerResponseDto = customerService.placeOrder(email, expectedDate);
        if (customerResponseDto.isPresent()) {
            return ResponseEntity.ok(customerResponseDto.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @GetMapping("/{pageNo}/{pageSize}/{direction}/{sortBy}")
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers(
            @PathVariable Integer pageNo,
            @PathVariable Integer pageSize,
            @PathVariable String direction,
            @PathVariable String sortBy    ) {
        Page<CustomerResponseDto> customerResponseDtos = customerService.findAll(pageNo, pageSize, direction, sortBy);
        if (customerResponseDtos.getTotalElements() > 0) {
            return ResponseEntity.ok(customerResponseDtos.getContent());        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping ("/add-customers")
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<Optional<List<CustomerResponseDto>>> createCustomers(@RequestBody List<CustomerRequestDto> customerRequestDtos    )
            throws URISyntaxException {
        Optional<List<CustomerResponseDto>> customerResponseDtos = customerService.addAllCustomers(customerRequestDtos);
        if (customerResponseDtos.isPresent()) {
            return ResponseEntity.created(new URI("/books")).body(customerResponseDtos);
        }
        return ResponseEntity.noContent().build();
    }
}
