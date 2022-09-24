package peaksoft.dto.mapper;

import org.springframework.stereotype.Component;
import peaksoft.dto.response.LoginResponse;
import peaksoft.entity.User;
import peaksoft.enums.Role;


@Component
public class Login {

    public LoginResponse toLoginView(String token, String message, User user) {
        var loginResponse = new LoginResponse();
        if (user != null) {
            getAuthority(loginResponse, user.getRole());
        }
        loginResponse.setMessage(message);
        loginResponse.setJwtToken(token);
        return loginResponse;
    }

    private void getAuthority(LoginResponse loginResponse, Role role) {
        loginResponse.setRole(role);
    }

}
