package miu.edu.cs.asd.deliverysystem.dto.delivery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import miu.edu.cs.asd.deliverysystem.model.DeliveryStatus;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryRequestDto {
    private LocalDate deliveryDate;
    private DeliveryStatus status;
    private String  email;
}
