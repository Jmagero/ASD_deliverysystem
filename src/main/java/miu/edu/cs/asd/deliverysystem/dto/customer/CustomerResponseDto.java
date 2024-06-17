package miu.edu.cs.asd.deliverysystem.dto.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"deliveries"})
public class CustomerResponseDto {
    private String email;
    private String contactNumber;
}
