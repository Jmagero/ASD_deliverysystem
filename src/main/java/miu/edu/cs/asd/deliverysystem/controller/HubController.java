package miu.edu.cs.asd.deliverysystem.controller;

import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import miu.edu.cs.asd.deliverysystem.dto.deliveryhub.HubRequestDto;
import miu.edu.cs.asd.deliverysystem.dto.deliveryhub.HubResponseDto;
import miu.edu.cs.asd.deliverysystem.service.hub.HubService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/hubs")
@RequiredArgsConstructor
public class HubController {
    private final HubService hubService;
    @GetMapping("/{hubName}")
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<HubResponseDto> getDriverHub(@PathVariable String hubName){
        Optional<HubResponseDto>  hubResponseDto = hubService.findByHubName(hubName);
        if(hubResponseDto.isPresent()){
            return ResponseEntity.ok(hubResponseDto.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<HubResponseDto> createHub(@Valid @RequestBody HubRequestDto hubRequestDto){
        Optional<HubResponseDto>  hubResponseDto = hubService.create(hubRequestDto);
        if(hubResponseDto.isPresent()){
            return ResponseEntity.ok(hubResponseDto.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{hubName}")
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<HubResponseDto> updateHub(@PathVariable String hubName,@Valid @RequestBody HubRequestDto hubRequestDto){
        Optional<HubResponseDto>  hubResponseDto = hubService.update(hubName, hubRequestDto);
        if(hubResponseDto.isPresent()){
            return ResponseEntity.ok(hubResponseDto.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<List<HubResponseDto>> getAllHubs(){
        Optional<List<HubResponseDto>> deliveryHubs = hubService.findAllHubs();
        if(deliveryHubs.isPresent()){
            return ResponseEntity.ok(deliveryHubs.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{hubName}")
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<HubResponseDto> deleteHub(@PathVariable String hubName){
        hubService.delete(hubName);
        return ResponseEntity.ok().build();
    }
}
