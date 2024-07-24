//package Starlink.starlink_access.controller;
//
//import Starlink.starlink_access.DTO.AuthDTO;
//import Starlink.starlink_access.DTO.AuthResponse;
//import Starlink.starlink_access.service.AuthService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/auth")
//@Tag(name = "Authentication", description = "Authentication management APIs")
//public class AuthController {
//
//    @Autowired
//    private AuthService authService;
//
//    @Operation(summary = "User login", description = "Authenticates a user and returns JWT tokens")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Successful authentication",
//                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
//            @ApiResponse(responseCode = "401", description = "Invalid credentials")
//    })
//    @PostMapping("/login")
//    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthDTO.LoginRequest loginRequest) {
//        return ResponseEntity.ok(authService.login(loginRequest));
//    }
//
//    @Operation(
//            summary = "User registration",
//            description = "Registers a new user and returns JWT tokens. " +
//                    "Password must meet the following requirements: " +
//                    "at least 8 characters long, " +
//                    "contain at least one uppercase letter, " +
//                    "one lowercase letter, " +
//                    "one digit, " +
//                    "and one special character."
//    )
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Successful registration",
//                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
//            @ApiResponse(responseCode = "400", description = "Invalid input, username already exists, or password doesn't meet requirements")
//    })
//    @PostMapping("/register")
//    public ResponseEntity<AuthResponse> register(
//            @Parameter(description = "Registration details. Note the password requirements in the endpoint description.")
//            @Valid @RequestBody AuthDTO.RegisterRequest registerRequest
//    ) {
//        return ResponseEntity.ok(authService.register(registerRequest));
//    }
//
//    @Operation(summary = "Refresh token", description = "Uses a refresh token to get a new access token")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "New access token generated",
//                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
//            @ApiResponse(responseCode = "401", description = "Invalid refresh token")
//    })
//    @PostMapping("/refresh")
//    public ResponseEntity<AuthResponse> refreshToken(
//            @Parameter(description = "Refresh token with 'Bearer ' prefix")
//            @RequestHeader("Authorization") String refreshToken
//    ) {
//        return ResponseEntity.ok(authService.refreshToken(refreshToken));
//    }
//}