package miu.edu.cs.asd.deliverysystem.service.customer;

import miu.edu.cs.asd.deliverysystem.dto.customer.CustomerDeliveryResponseDto;
import miu.edu.cs.asd.deliverysystem.dto.customer.CustomerRequestDto;
import miu.edu.cs.asd.deliverysystem.dto.customer.CustomerResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Optional<CustomerResponseDto> findByEmail(String email);
    Optional<CustomerResponseDto> updateCustomer(String email, CustomerRequestDto customerRequestDto);
    Optional<CustomerResponseDto> addCustomer(CustomerRequestDto customerRequestDto);
    void  deleteCustomer(String email);

    Optional<CustomerDeliveryResponseDto> placeOrder(String email, String expectedDate);
    Optional<List<CustomerResponseDto>> addAllCustomers(List<CustomerRequestDto> customerRequestDtos);
    Page<CustomerResponseDto> findAll(int pageNumber, int pageSize, String direction, String sortBy);
}
