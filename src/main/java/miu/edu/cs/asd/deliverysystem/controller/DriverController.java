package miu.edu.cs.asd.deliverysystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import miu.edu.cs.asd.deliverysystem.dto.driver.DriverRequestDto;
import miu.edu.cs.asd.deliverysystem.dto.driver.DriverResponseDto;
import miu.edu.cs.asd.deliverysystem.service.driver.DriverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;
    @GetMapping
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<List<DriverResponseDto>> getAllDrivers() {
        Optional<List<DriverResponseDto>> drivers = driverService.getAllDrivers();
        if (drivers.isPresent()) {
            return ResponseEntity.ok(drivers.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<?> createDriver(@Valid @RequestBody DriverRequestDto requestDto) {
        Optional<DriverResponseDto> driver = driverService.createDriver(requestDto);
        if (driver.isPresent()) {
            return new ResponseEntity<>(driver.get(), HttpStatus.CREATED);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access is denied");
    }

    @GetMapping("/{email}")
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<DriverResponseDto> getDriver(@PathVariable String email) {
        Optional<DriverResponseDto> driverResponseDto = driverService.getDriverByEmail(email);
        if (driverResponseDto.isPresent()) {
            return ResponseEntity.ok(driverResponseDto.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{email}")
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<DriverResponseDto> updateDriver(@PathVariable String email, @Valid @RequestBody DriverRequestDto requestDto) {
        Optional<DriverResponseDto> driverResponseDto = driverService.updateDriver(email, requestDto);
        if (driverResponseDto.isPresent()) {
            return ResponseEntity.ok(driverResponseDto.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{driver}")
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<Void> deleteDriver(@PathVariable String driver) {
        driverService.deleteDriver(driver);
        return ResponseEntity.noContent().build();
    }

    //delivery to customer

}
