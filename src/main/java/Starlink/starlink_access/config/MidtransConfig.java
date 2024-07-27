package Starlink.starlink_access.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
public class MidtransConfig {
    private final String SERVER_KEY = "SB-Mid-server-30iQHtDfOsjD5qz2TDuKt9Iv";

    public HttpHeaders getServerHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();
        String auth = SERVER_KEY + ":";
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);

        httpHeaders.set("Authorization", authHeader);
        httpHeaders.set("Content-Type", "application/json");

        return httpHeaders;
    }
}
