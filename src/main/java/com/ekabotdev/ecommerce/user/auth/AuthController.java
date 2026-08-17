package com.ekabotdev.ecommerce.user.auth;


import com.ekabotdev.ecommerce.user.dto.LoginRequest;
import com.ekabotdev.ecommerce.user.dto.LoginResponse;
import com.ekabotdev.ecommerce.user.dto.RegisterRequest;
import com.ekabotdev.ecommerce.user.dto.RegisterResponse;
import com.ekabotdev.ecommerce.user.entity.User;
import com.ekabotdev.ecommerce.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    public AuthController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register( @Valid @RequestBody RegisterRequest request) {

        User user = userService.register(request);

        RegisterResponse response = new RegisterResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login( @Valid @RequestBody LoginRequest request) {
        String token = authenticationService.authenticate(request);
        LoginResponse response = new LoginResponse(token,"Bearer");
        return ResponseEntity.ok(response);
    }
}
