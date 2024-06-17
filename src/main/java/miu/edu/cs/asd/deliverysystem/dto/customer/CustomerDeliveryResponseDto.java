package miu.edu.cs.asd.deliverysystem.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDeliveryResponseDto {
    private String email;
    private String contactNumber;
    List<DeliverySummaryDto> deliverySummaryDtos;
}
