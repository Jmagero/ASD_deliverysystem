package miu.edu.cs.asd.deliverysystem.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import miu.edu.cs.asd.deliverysystem.dto.address.AddressRequestDto;
import miu.edu.cs.asd.deliverysystem.dto.payment.PaymentRequestDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequestDto {
    private String customerName;
    private String contactNumber;
    private String email;
    private AddressRequestDto addressRequestDto;
    private PaymentRequestDto paymentRequestDto;
}
