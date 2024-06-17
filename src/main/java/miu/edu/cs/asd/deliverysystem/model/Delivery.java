package miu.edu.cs.asd.deliverysystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "deliveries")
@NoArgsConstructor
@Data
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deliveryDate;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
    @ManyToOne()
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @ManyToOne()
    @JoinColumn(name = "hub_id")
    private DeliveryHub deliveryHub;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Delivery(LocalDate deliveryDate, DeliveryStatus status, Driver driver, DeliveryHub deliveryHub, Customer customer) {
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.driver = driver;
        this.deliveryHub = deliveryHub;
        this.customer = customer;
    }
}
