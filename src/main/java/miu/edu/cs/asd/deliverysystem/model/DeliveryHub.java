package miu.edu.cs.asd.deliverysystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "deliveryhubs")
@Data
public class DeliveryHub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hubId;
    @Column(unique = true, nullable = false)
    @NotBlank(message = "hubName cannot be blank")
    private String hubName;
    @NotBlank(message = "contactPhone cannot be blank")
    private String contactPhone;
    @OneToOne(cascade = CascadeType.PERSIST)
    private Address address;
}
