package miu.edu.cs.asd.deliverysystem.service.user;

import lombok.RequiredArgsConstructor;
import miu.edu.cs.asd.deliverysystem.Repository.UserRepository;
import miu.edu.cs.asd.deliverysystem.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Override
    public void addAllUsers(List<User> users) {
        userRepository.saveAll(users);
    }
}
