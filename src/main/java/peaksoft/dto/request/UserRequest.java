package peaksoft.dto.request;


import lombok.Getter;
import lombok.Setter;
import peaksoft.enums.Role;
import peaksoft.validation.Password;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class UserRequest {
    private String email;
    @Password
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

}
