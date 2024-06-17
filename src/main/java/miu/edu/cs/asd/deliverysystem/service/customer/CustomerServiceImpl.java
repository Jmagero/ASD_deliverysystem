package miu.edu.cs.asd.deliverysystem.service.customer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.cs.asd.deliverysystem.Repository.*;
import miu.edu.cs.asd.deliverysystem.dto.customer.*;
import miu.edu.cs.asd.deliverysystem.model.*;
import miu.edu.cs.asd.deliverysystem.service.delivery.DeliveryService;
import miu.edu.cs.asd.deliverysystem.service.driver.DriverService;
import miu.edu.cs.asd.deliverysystem.service.hub.HubService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final DriverRepository driverRepository;
    private final DeliveryHubRepository driverHubRepository;
    private final DeliveryRepository deliveryRepository;
    private final DriverService driverService;
    private final DeliveryService deliveryService;
    private final ModelMapper modelMapper;
    private final HubService hubService;
    private final DeliveryHubRepository deliveryHubRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(CustomerService.class);
    @Override
    @Transactional
    public Optional<CustomerResponseDto> addCustomer(CustomerRequestDto customerRequestDto) {
        User user = new User(customerRequestDto.getCustomerName(), customerRequestDto.getCustomerName(),
                customerRequestDto.getEmail(),
                passwordEncoder.encode("default"),
                Role.MEMBER);
        userRepository.save(user);
        logger.info("Customer created");
        Customer customer = modelMapper.map(customerRequestDto, Customer.class);
        Customer savedCustomer = customerRepository.save(customer);
        logger.info("Customer saved: {}", savedCustomer);
        CustomerResponseDto customerResponseDto = modelMapper.map(savedCustomer, CustomerResponseDto.class);
        return Optional.of(customerResponseDto);
    }

    @Override
    public Optional<CustomerResponseDto> findByEmail(String email) {
        Optional<Customer>  customer = customerRepository.findByEmail(email);
        if (customer.isPresent()) {
            CustomerResponseDto customerResponseDto = modelMapper.map(customer.get(), CustomerResponseDto.class);
            return Optional.of(customerResponseDto);
        }
        return Optional.empty();
    }

    @Override
    public Optional<CustomerResponseDto> updateCustomer(String email, CustomerRequestDto customerRequestDto) {
        Optional<Customer>  customer = customerRepository.findByEmail(email);
        if (customer.isPresent()) {
            modelMapper.map(customerRequestDto, customer.get());
            Customer savedCustomer = customerRepository.save(customer.get());
            CustomerResponseDto customerResponseDto = modelMapper.map(savedCustomer, CustomerResponseDto.class);
            return Optional.of(customerResponseDto);
        }
        return Optional.empty();
    }



    @Override
    public void deleteCustomer(String email) {
        Optional<Customer>  customer = customerRepository.findByEmail(email);
        if (customer.isPresent()) {
            customerRepository.delete(customer.get());
        }
    }

    @Override
    public Optional<CustomerDeliveryResponseDto> placeOrder(String email, String expectedDate) {
        Optional<Customer>  customer = customerRepository.findByEmail(email);
        Driver driver = driverRepository.findAll().stream().filter(driver1 -> !driver1.isAssigned()).findFirst().get();
        DeliveryHub deliveryHub = deliveryHubRepository.findAll().stream().findFirst().get();
        if (customer.isPresent()) {
            driver = driverRepository.findAll().stream().filter(driver1 -> !driver1.isAssigned()).findFirst().get();
            deliveryHub = deliveryHubRepository.findAll().stream().findFirst().get();
            Delivery delivery = new Delivery();
            delivery.setCustomer(customer.get());
            delivery.setDriver(driver);
            delivery.setDeliveryHub(deliveryHub);
            delivery.setDeliveryDate(LocalDate.parse(expectedDate));
            delivery.setStatus(DeliveryStatus.PENDING);
            Delivery savedDelivery = deliveryRepository.save(delivery);
            List<Delivery> customerDeliveries = customer.get().getDeliveries();
            customerDeliveries.add(savedDelivery);
            driver.setAssigned(true);
            driverRepository.save(driver);
        }
        List<Delivery> customerDeliveries = customer.get().getDeliveries();
        List<DeliverySummaryDto> deliverySummaryDtos =  customerDeliveries.stream()
                .map(delivery -> new DeliverySummaryDto(
                        delivery.getDeliveryDate(),
                        delivery.getStatus(),
                        delivery.getDriver().getDriverName()
                )).toList();
        CustomerDeliveryResponseDto customerDeliveryResponseDto = new CustomerDeliveryResponseDto();
        customerDeliveryResponseDto.setDeliverySummaryDtos(deliverySummaryDtos);
        customerDeliveryResponseDto.setEmail(customer.get().getEmail());
        customerDeliveryResponseDto.setContactNumber(customer.get().getContactNumber());

        return Optional.of(customerDeliveryResponseDto);
    }

    @Override
    public Optional<List<CustomerResponseDto>> addAllCustomers(List<CustomerRequestDto> customerRequestDtos) {
        List<User> users = customerRequestDtos.stream().map(
                customerRequestDto -> {
                    return new User(customerRequestDto.getCustomerName(), customerRequestDto.getCustomerName(),
                            customerRequestDto.getEmail(),
                            passwordEncoder.encode("default"),
                            Role.MEMBER);
                }
        ).toList();
        List<Customer> customerList = modelMapper.map(customerRequestDtos, new TypeToken<List<Customer>>() {}.getType());
        //Save all customers
        List<Customer> savedCustomerList = customerRepository.saveAll(customerList);
        //Map from List<Customer> to List<CustomerResponseDto>
        List<CustomerResponseDto> customerResponseDtoList =
                modelMapper.map(savedCustomerList, new TypeToken<List<CustomerResponseDto>>() {}.getType());
        return Optional.of(customerResponseDtoList);
    }
    @Override
    public Page<CustomerResponseDto> findAll(int pageNumber, int pageSize, String direction, String sortBy) {
        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                Sort.Direction.fromString(direction),
                sortBy
        );
        //It returns a page(mentioned in pageable object) of book entities
        Page<Customer> customerPage = customerRepository.findAll(pageable);
        List<CustomerResponseDto> customerResponseDtoList =
                customerPage.stream()
                        .map(book -> modelMapper.map(book, CustomerResponseDto.class))
                        .collect(Collectors.toList());
        System.out.println("Intermediate result: " + customerResponseDtoList);
        return new PageImpl<>(
                customerResponseDtoList,
                pageable,
                customerPage.getTotalElements()
        );
    }
}
