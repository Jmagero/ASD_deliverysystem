package miu.edu.cs.asd.deliverysystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
@NoArgsConstructor
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    private BigDecimal amount;
    private LocalDate paymentDate;

    public Payment(LocalDate paymentDate, BigDecimal amount) {
        this.paymentDate = paymentDate;
        this.amount = amount;
    }
}
