package miu.edu.cs.asd.deliverysystem.dto.deliveryhub;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import miu.edu.cs.asd.deliverysystem.model.Address;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HubRequestDto {
    private String hubName;
    private String contactPhone;
    private Address address;
}
