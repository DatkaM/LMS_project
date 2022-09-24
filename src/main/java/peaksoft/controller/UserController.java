package peaksoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.UserResponse;
import peaksoft.dto.search.UserResponseView;
import peaksoft.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class UserController {

    private  final UserService service;

    @PostMapping
    public UserResponse create(@RequestBody @Valid UserRequest request){
        return service.create(request);
    }
}
