package miu.edu.cs.asd.deliverysystem.service.delivery;

import lombok.RequiredArgsConstructor;
import miu.edu.cs.asd.deliverysystem.Repository.*;
import miu.edu.cs.asd.deliverysystem.dto.customer.*;
import miu.edu.cs.asd.deliverysystem.dto.delivery.DeliveryRequestDto;
import miu.edu.cs.asd.deliverysystem.dto.delivery.DeliveryResponseDto;
import miu.edu.cs.asd.deliverysystem.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final CustomerRepository customerRepository;
    private final DriverRepository driverRepository;
    private final DeliveryHubRepository deliveryHubRepository;
    private final ModelMapper modelMapper;

    @Override
    public Optional<List<DriverDeliveryResponse>> findAllByDriver(String driverEmail) {
        Optional<Driver> driver = driverRepository.findByEmail(driverEmail);
        if (driver.isPresent()) {
            List<Delivery> deliveryList = deliveryRepository.findAllByDriver(driver.get());
            List<DriverDeliveryResponse> driverDeliveryResponses = deliveryList.stream()
                    .map( d -> modelMapper.map(d, DriverDeliveryResponse.class) ).toList();
            return Optional.of(driverDeliveryResponses);
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<CustomerDeliveryResponseDto>> findAllByCustomer(String customerEmail) {
        Optional<Customer> customer = customerRepository.findByEmail(customerEmail);
        if (customer.isPresent()) {
            List<Delivery> customerDeliveries = deliveryRepository.findAllByCustomer(customer.get());
            List<DeliverySummaryDto> deliverySummaryDtos =  customerDeliveries.stream()
                    .map(delivery -> DeliverySummaryDto .builder()
                                    .deliveryDate(delivery.getDeliveryDate())
                                    .status(delivery.getStatus())
                                    .driver(delivery.getDriver().getDriverName())
                                    .build()
                    ).toList();
            List<CustomerDeliveryResponseDto> customerDeliveryResponseDtos = deliverySummaryDtos.stream()
                    .map(deliverySummaryDto -> {
                        CustomerDeliveryResponseDto customerDeliveryResponseDto = new CustomerDeliveryResponseDto();
                        customerDeliveryResponseDto.setDeliverySummaryDtos(deliverySummaryDtos);
                        customerDeliveryResponseDto.setEmail(customer.get().getEmail());
                        customerDeliveryResponseDto.setContactNumber(customer.get().getContactNumber());
                        return customerDeliveryResponseDto;
                    }).toList();

//            List<DeliveryResponseDto> deliveryResponseDtos = modelMapper.map(deliveryList, new TypeToken<List<DeliveryResponseDto>>() {}.getType());
            return Optional.of(customerDeliveryResponseDtos);
        }
        return Optional.empty();

    }
    //to be continued
    @Override
    public Optional<DeliveryResponseDto> updateDelivery(Long id, DeliveryRequestDto deliveryRequestDto) {
        Optional<Delivery> delivery = deliveryRepository.findById(id);
        if (delivery.isPresent()) {
            modelMapper.map(deliveryRequestDto, delivery.get());
            Delivery savedDelivery = deliveryRepository.save(delivery.get());
            return Optional.of(modelMapper.map(savedDelivery, DeliveryResponseDto.class));
        }
        return Optional.empty();
    }

    @Override
    public Optional<DeliveryResponseDto> createDelivery(DeliveryRequestDto deliveryRequestDto) {
        Optional<Customer> customer = customerRepository.findByEmail(deliveryRequestDto.getEmail());
        Driver driver = driverRepository.findAll().stream().filter(driver1 -> !driver1.isAssigned()).findFirst().get();
        DeliveryHub deliveryHub = deliveryHubRepository.findAll().stream().findFirst().get();
        Delivery delivery = new Delivery();
        delivery.setCustomer(customer.get());
        delivery.setDriver(driver);
        delivery.setDeliveryHub(deliveryHub);
        delivery.setDeliveryDate(deliveryRequestDto.getDeliveryDate());
        delivery.setStatus(deliveryRequestDto.getStatus());
        Delivery savedDelivery = deliveryRepository.save(delivery);
        DeliveryResponseDto deliveryResponseDto = modelMapper.map(savedDelivery, DeliveryResponseDto.class);
        return Optional.of(deliveryResponseDto);
    }

    @Override
    public Optional<DeliveryResponseDto> findById(Long id) {
        Optional<Delivery> delivery = deliveryRepository.findById(id);
        if (delivery.isPresent()) {
            modelMapper.map(delivery.get(), DeliveryResponseDto.class);
            DeliveryResponseDto deliveryResponseDto = modelMapper.map(delivery.get(), DeliveryResponseDto.class);
            return Optional.of(deliveryResponseDto);
        }
        return Optional.empty();
    }
}
