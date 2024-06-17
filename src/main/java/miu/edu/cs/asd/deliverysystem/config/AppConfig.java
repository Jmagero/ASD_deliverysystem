package miu.edu.cs.asd.deliverysystem.config;

import miu.edu.cs.asd.deliverysystem.dto.address.AddressRequestDto;
import miu.edu.cs.asd.deliverysystem.dto.customer.CustomerRequestDto;
import miu.edu.cs.asd.deliverysystem.dto.customer.CustomerResponseDto;
import miu.edu.cs.asd.deliverysystem.dto.deliveryhub.HubRequestDto;
import miu.edu.cs.asd.deliverysystem.dto.deliveryhub.HubResponseDto;
import miu.edu.cs.asd.deliverysystem.dto.driver.DriverResponseDto;
import miu.edu.cs.asd.deliverysystem.dto.payment.PaymentRequestDto;
import miu.edu.cs.asd.deliverysystem.model.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(
                new PropertyMap<HubRequestDto, DeliveryHub>() {
                    @Override
                    protected void configure() {
                        map().setHubName(source.getHubName());
                        map().setContactPhone(source.getContactPhone());
                        map().setAddress(source.getAddress());
                    }
                }
        );
        modelMapper.addMappings(
                new PropertyMap<DeliveryHub, HubResponseDto>() {
                    @Override
                    protected void configure() {
                        map().setHubName(source.getHubName());
                        map().setContactPhone(source.getContactPhone());
                    }
                }
        );
        modelMapper.addMappings(
                new PropertyMap<Driver, DriverResponseDto>() {
                    @Override
                    protected void configure() {
                        map().setDriverName(source.getDriverName());
                        map().setPhone(source.getPhone());
                        map().setDeliveriesResponseDto(source.getDeliveries());
                    }
                }
        );
        modelMapper.addMappings(
                new PropertyMap<Customer, CustomerResponseDto>() {
                    @Override
                    protected void configure() {
                        map().setEmail(source.getEmail());
                        map().setContactNumber(source.getContactNumber());
                    }
                }
        );
        modelMapper.addMappings(
                new PropertyMap<CustomerRequestDto, Customer>() {

                    @Override
                    protected void configure() {
                        map().setEmail(source.getEmail());
                        map().setContactNumber(source.getContactNumber());
                        map().setCustomerName(source.getCustomerName());
                        using((ctx) -> modelMapper.map(ctx.getSource(), Address.class)).map(source.getAddressRequestDto(), destination.getAddress());
                        using((ctx) -> modelMapper.map(ctx.getSource(), Payment.class)).map(source.getPaymentRequestDto(), destination.getPayment());


                        //map().setAddress(map(source.getAddressRequestDto(), Address.class));

//                        map().setAddress(source.getAddressRequestDto());
//                        map().setPayment(source.getPaymentRequestDto());
                    }
                }
        );
        modelMapper.addMappings(new PropertyMap<AddressRequestDto, Address>() {
            @Override
            protected void configure() {
                map().setStreet(source.getStreet());
                map().setCity(source.getCity());
                map().setState(source.getState());
            }
        });

        // Add more mappings as necessary for PaymentRequestDto to Payment
        // Assuming PaymentRequestDto and Payment are similarly structured
        modelMapper.addMappings(new PropertyMap<PaymentRequestDto, Payment>() {
            @Override
            protected void configure() {
                map().setAmount(source.getAmount());
                map().setPaymentDate(source.getPaymentDate());
            }
        });
        return modelMapper;
    }
}
