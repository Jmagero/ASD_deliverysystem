package miu.edu.cs.asd.deliverysystem.dto.delivery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import miu.edu.cs.asd.deliverysystem.dto.customer.CustomerResponseDto;
import miu.edu.cs.asd.deliverysystem.dto.deliveryhub.HubResponseDto;
import miu.edu.cs.asd.deliverysystem.dto.driver.DriverResponseDto;
import miu.edu.cs.asd.deliverysystem.model.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryResponseDto {
    private LocalDate deliveryDate;
    private DeliveryStatus status;
    private DriverResponseDto driver;
    private HubResponseDto deliveryHub;
    private CustomerResponseDto customer;
}
