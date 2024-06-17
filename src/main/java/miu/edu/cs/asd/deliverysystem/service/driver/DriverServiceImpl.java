package miu.edu.cs.asd.deliverysystem.service.driver;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.RequiredArgsConstructor;
import miu.edu.cs.asd.deliverysystem.Repository.DriverRepository;
import miu.edu.cs.asd.deliverysystem.Repository.UserRepository;
import miu.edu.cs.asd.deliverysystem.dto.driver.DriverRequestDto;
import miu.edu.cs.asd.deliverysystem.dto.driver.DriverResponseDto;
import miu.edu.cs.asd.deliverysystem.model.Driver;
import miu.edu.cs.asd.deliverysystem.model.Role;
import miu.edu.cs.asd.deliverysystem.model.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Optional<DriverResponseDto> getDriverByEmail(String email) {
        Optional<Driver> driver = driverRepository.findByEmail(email);
        if (driver.isPresent()) {
            modelMapper.map(driver.get(), DriverResponseDto.class);
            DriverResponseDto driverResponseDto = modelMapper.map(driver.get(), DriverResponseDto.class);
            return Optional.of(driverResponseDto);
        }
        return Optional.empty();
    }


    @Override
    public Optional<DriverResponseDto> createDriver(DriverRequestDto driverRequestDto) {
        User user = new User(driverRequestDto.getDriverName(), driverRequestDto.getDriverName(),
                driverRequestDto.getEmail(),
                passwordEncoder.encode("default"),
                Role.MEMBER);
        userRepository.save(user);

        Driver driver = modelMapper.map(driverRequestDto, Driver.class);
        Driver savedDriver = driverRepository.save(driver);
        DriverResponseDto driverResponseDto = modelMapper.map(savedDriver, DriverResponseDto.class);
        return Optional.of(driverResponseDto);
    }

    @Override
    public Optional<DriverResponseDto> updateDriver(String email, DriverRequestDto driverRequestDto) {
        Optional<Driver> driver = driverRepository.findByEmail(email);
        if (driver.isPresent()) {
            modelMapper.map(driverRequestDto, driver.get());
            Driver savedDriver = driverRepository.save(driver.get());
            DriverResponseDto driverResponseDto = modelMapper.map(savedDriver, DriverResponseDto.class);
            return Optional.of(driverResponseDto);
        }
        return Optional.empty();
    }

    @Override
    public void deleteDriver(String email) {
        Optional<Driver> driver = driverRepository.findByEmail(email);
        if (driver.isPresent()) {
            driverRepository.delete(driver.get());
        }
    }

    @Override
    public Optional<List<DriverResponseDto>> getAllDrivers() {
        List<Driver> drivers = driverRepository.findAll();
        if (drivers.isEmpty()) {
            return Optional.empty();
        }
        List<DriverResponseDto> driverResponseDtos = modelMapper.map(drivers, new TypeToken<List<DriverResponseDto>>() {}.getType() );
        return Optional.of(driverResponseDtos);
    }
}
