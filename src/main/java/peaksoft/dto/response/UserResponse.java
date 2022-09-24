package peaksoft.dto.response;

import lombok.Getter;
import lombok.Setter;
import peaksoft.enums.Role;


@Getter
@Setter
public class UserResponse {
    private String email;
    private String jwtToken;
    private Role role;

}
