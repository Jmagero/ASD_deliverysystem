package miu.edu.cs.asd.deliverysystem.service.hub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.cs.asd.deliverysystem.Repository.DeliveryHubRepository;
import miu.edu.cs.asd.deliverysystem.dto.deliveryhub.HubRequestDto;
import miu.edu.cs.asd.deliverysystem.dto.deliveryhub.HubResponseDto;
import miu.edu.cs.asd.deliverysystem.model.DeliveryHub;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class HubServiceImpl  implements HubService {
    private final ModelMapper modelMapper;
    private final DeliveryHubRepository deliveryHubRepository;
    private Logger logger = LoggerFactory.getLogger(HubServiceImpl.class);
    @Override
    public Optional<HubResponseDto> findByHubName(String name) {
        Optional<DeliveryHub> deliveryHub = deliveryHubRepository.findByHubName(name);
        if (deliveryHub.isPresent()) {
            modelMapper.map(deliveryHub.get(), HubResponseDto.class);
            DeliveryHub savedDeliveryHub = deliveryHubRepository.save(deliveryHub.get());
            HubResponseDto hubResponseDto = modelMapper.map(savedDeliveryHub, HubResponseDto.class);
            return Optional.of(hubResponseDto);
        }
        return Optional.empty();
    }

    @Override
    public Optional<HubResponseDto> create(HubRequestDto hubRequestDto) {
        DeliveryHub deliveryHub = modelMapper.map(hubRequestDto, DeliveryHub.class);
        logger.info("deliveryHub:  " ,deliveryHub.toString());
        DeliveryHub savedDeliveryHub = deliveryHubRepository.save(deliveryHub);
        HubResponseDto hubResponseDto = modelMapper.map(savedDeliveryHub, HubResponseDto.class);
        return Optional.of(hubResponseDto);
    }

    @Override
    public Optional<HubResponseDto> update(String name, HubRequestDto hubRequestDto) {
        Optional<DeliveryHub> deliveryHub = deliveryHubRepository.findByHubName(name);
        if (deliveryHub.isPresent()) {
            modelMapper.map(hubRequestDto, deliveryHub.get());
            DeliveryHub savedDeliveryHub = deliveryHubRepository.save(deliveryHub.get());
            HubResponseDto hubResponseDto = modelMapper.map(savedDeliveryHub, HubResponseDto.class);
            return Optional.of(hubResponseDto);
        }
        return Optional.empty();
    }

    @Override
    public void delete(String name) {
        Optional<DeliveryHub> deliveryHub = deliveryHubRepository.findByHubName(name);
        if (deliveryHub.isPresent()) {
            deliveryHubRepository.delete(deliveryHub.get());
        }
    }

    @Override
    public Optional<List<HubResponseDto>> findAllHubs() {
        List<DeliveryHub> deliveryHubs = deliveryHubRepository.findAll();
        List<HubResponseDto> hubResponseDtos = modelMapper.map(deliveryHubs, new TypeToken<List<HubResponseDto>>() {}.getType());
        return Optional.of(hubResponseDtos);
    }
}
