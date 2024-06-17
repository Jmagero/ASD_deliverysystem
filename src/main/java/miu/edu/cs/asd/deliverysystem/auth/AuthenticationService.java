package miu.edu.cs.asd.deliverysystem.auth;

import lombok.RequiredArgsConstructor;
import miu.edu.cs.asd.deliverysystem.Repository.UserRepository;
import miu.edu.cs.asd.deliverysystem.config.JwtService;
import miu.edu.cs.asd.deliverysystem.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        //create user by using data from register request
        User user = new User(
                registerRequest.getFirstName(),
                registerRequest.getLastName(),
                registerRequest.getUsername(),
                passwordEncoder.encode(registerRequest.getPassword()),
                registerRequest.getRole()
        );
        //save this user
        User savedUser = userRepository.save(user);
        //generate a token for this user
        String token = jwtService.generateToken(savedUser);

        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        var user = userRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        //var user = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }
}
