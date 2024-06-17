package miu.edu.cs.asd.deliverysystem.dto.driver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverRequestDto {
    private String driverName;
    private String email;
    private String phone;
    private boolean assigned;
    private String vehicleInfo;
}
