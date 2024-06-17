package miu.edu.cs.asd.deliverysystem.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import miu.edu.cs.asd.deliverysystem.model.Address;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerAddressRespDto {
    private String contactNumber;
   private Address address;
}
