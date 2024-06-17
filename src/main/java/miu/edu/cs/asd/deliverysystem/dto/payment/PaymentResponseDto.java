package miu.edu.cs.asd.deliverysystem.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDto {
    private BigDecimal amount;
    private LocalDate paymentDate;
}
