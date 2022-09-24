package peaksoft.controller;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.dto.request.LoginRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.LoginResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.dto.mapper.Login;
import peaksoft.entity.User;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repositories.UserRepository;
import peaksoft.security_mvc_file.jwt.JwtTokenUtil;
import peaksoft.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jwt")
public class AuthController {

    private final UserService userService;
    private final UserRepository repository;
    private final JwtTokenUtil jwtTokenUtil;
    private final Login login;
    private final AuthenticationManager authenticationManager;


    @PostMapping("login")
    public ResponseEntity<LoginResponse> getLogin(@RequestBody LoginRequest request){
        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
            authenticationManager.authenticate(token);
            User user = repository.findByEmail(token.getName()).orElseThrow(
                    () -> new NotFoundException(String.format(
                            "User with %s name not found",token.getName()
                    ))
            );
            return ResponseEntity.ok().body(login.toLoginView(jwtTokenUtil.generateToken(user),
                    "Successfully",user)
            );
        }catch (BadCredentialsException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(login.toLoginView("","Login_failed",null));
        }
    }

    @PostMapping("registration")
    public UserResponse create(@RequestBody UserRequest request){
        return userService.create(request);
    }
}