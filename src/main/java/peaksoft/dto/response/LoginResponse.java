package peaksoft.dto.response;

import lombok.Getter;
import lombok.Setter;
import peaksoft.enums.Role;

import java.util.Set;

@Getter
@Setter
public class LoginResponse {

    private String jwtToken;
    private String message;
    private Role role;
}
