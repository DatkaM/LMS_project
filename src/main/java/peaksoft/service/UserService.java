package peaksoft.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.dto.mapper.UserMapper;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.UserResponse;
import peaksoft.dto.search.UserResponseView;
import peaksoft.entity.User;
import peaksoft.repositories.UserRepository;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    public UserResponse create(UserRequest request) {
        User user = mapper.mapToEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return mapper.mapToResponse(userRepository.save(user));
    }

}