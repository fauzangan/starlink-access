package Starlink.starlink_access.service;


import Starlink.starlink_access.DTO.AuthDTO;
import Starlink.starlink_access.DTO.AuthResponse;
import Starlink.starlink_access.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public interface AuthService {
    AuthResponse login(AuthDTO.LoginRequest loginRequest);
    AuthResponse register(AuthDTO.RegisterRequest registerRequest);
    AuthResponse refreshToken(String refreshToken);
    User getUserAuthenticated();
}