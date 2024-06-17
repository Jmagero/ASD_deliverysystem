package miu.edu.cs.asd.deliverysystem.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import miu.edu.cs.asd.deliverysystem.dto.deliveryhub.HubResponseDto;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriverDeliveryResponse {
        private LocalDate deliveryDate;
        private HubResponseDto  deliveryHub;
        private CustomerAddressRespDto customer;
}
