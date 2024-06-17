package miu.edu.cs.asd.deliverysystem.service.driver;

import miu.edu.cs.asd.deliverysystem.dto.driver.DriverRequestDto;
import miu.edu.cs.asd.deliverysystem.dto.driver.DriverResponseDto;

import java.util.List;
import java.util.Optional;

public interface DriverService {
    Optional<DriverResponseDto> getDriverByEmail(String email);
    Optional<DriverResponseDto> createDriver(DriverRequestDto driverRequestDto);
    Optional<DriverResponseDto> updateDriver(String email, DriverRequestDto driverRequestDto);
    void deleteDriver(String email);
    Optional<List<DriverResponseDto>> getAllDrivers();
}
