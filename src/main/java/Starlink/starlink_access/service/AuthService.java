package Starlink.starlink_access.service;


import Starlink.starlink_access.DTO.AuthDTO;
import Starlink.starlink_access.DTO.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthDTO.LoginRequest loginRequest);
    AuthResponse register(AuthDTO.RegisterRequest registerRequest);
    AuthResponse refreshToken(String refreshToken);
}