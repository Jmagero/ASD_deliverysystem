package miu.edu.cs.asd.deliverysystem.dto.deliveryhub;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HubResponseDto {
    private String hubName;
    private String contactPhone;
}
