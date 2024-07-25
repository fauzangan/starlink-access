package Starlink.starlink_access.controller;

import Starlink.starlink_access.DTO.UserDTO;
import Starlink.starlink_access.repository.UserRepository;
import Starlink.starlink_access.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "User management APIs")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/profile")
    @Operation(summary = "Get user profile", description = "Retrieves the profile of the authenticated user")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of user profile",
            content = @Content(schema = @Schema(implementation = UserDTO.class)))
    public ResponseEntity<UserDTO> getUserProfile(Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }

    @PutMapping("/profile")
    @Operation(summary = "Update user profile", description = "Updates the profile of the authenticated user")
    @ApiResponse(responseCode = "200", description = "Successful update of user profile",
            content = @Content(schema = @Schema(implementation = UserDTO.class)))
    public ResponseEntity<UserDTO> updateUserProfile(
            Authentication authentication,
            @Valid @RequestBody UserDTO userDto) {
        Long userId = getUserIdFromAuthentication(authentication);
        return ResponseEntity.ok(userService.updateUserProfile(userId, userDto));
    }

    private Long getUserIdFromAuthentication(Authentication authentication) {
        return userRepository.findByUsername(authentication.getName()).get().getId();
    }
}