package peaksoft.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.UserResponse;
import peaksoft.entity.User;
import peaksoft.exceptions.BadRequestException;
import peaksoft.repositories.UserRepository;
import peaksoft.security_mvc_file.jwt.JwtTokenUtil;


@Component
@RequiredArgsConstructor
public class UserMapper {
    private final UserRepository repository;


    public User mapToEntity(UserRequest request) {
        User user = new User();
        boolean exists = repository.existsByEmail(request.getEmail());
        if (exists) {
            throw new BadRequestException("Already have a similar email");
        }

        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        return user;
    }

    public UserResponse mapToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setJwtToken("Login and you will be given a new token for further use");
        return response;
    }
}
