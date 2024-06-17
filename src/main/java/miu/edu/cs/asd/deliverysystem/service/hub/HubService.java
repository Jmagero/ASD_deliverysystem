package miu.edu.cs.asd.deliverysystem.service.hub;

import miu.edu.cs.asd.deliverysystem.dto.deliveryhub.HubRequestDto;
import miu.edu.cs.asd.deliverysystem.dto.deliveryhub.HubResponseDto;

import java.util.List;
import java.util.Optional;

public interface HubService {
    Optional<HubResponseDto> findByHubName(String name);
    Optional<HubResponseDto> create(HubRequestDto hubRequestDto);
    Optional<HubResponseDto> update(String name, HubRequestDto hubRequestDto);
    void delete(String name);
    Optional<List<HubResponseDto>> findAllHubs();
}
