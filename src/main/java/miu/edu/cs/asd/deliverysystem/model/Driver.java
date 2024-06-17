package miu.edu.cs.asd.deliverysystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "drivers")
@NoArgsConstructor
@Getter
@Setter
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long driverId;
    @NotBlank(message = "driverName can not be blank")
    private String driverName;
    @NotBlank(message = "email cannot be blank")
    @Column(unique = true)
    private String email;
    private String phone;
    private String vehicleInfo;
    private boolean assigned;
    @OneToMany(mappedBy = "driver")
    List<Delivery>  deliveries;

    public Driver(String driverName, String email, String phone, String vehicleInfo) {
        this.driverName = driverName;
        this.email = email;
        this.phone = phone;
        this.vehicleInfo = vehicleInfo;
    }
}
