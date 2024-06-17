package miu.edu.cs.asd.deliverysystem.service.user;

import miu.edu.cs.asd.deliverysystem.dto.customer.CustomerResponseDto;
import miu.edu.cs.asd.deliverysystem.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void addAllUsers(List<User> users);
}
