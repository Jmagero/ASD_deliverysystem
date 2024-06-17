package miu.edu.cs.asd.deliverysystem.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import miu.edu.cs.asd.deliverysystem.model.Role;

@Data
@AllArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Role role;
}
