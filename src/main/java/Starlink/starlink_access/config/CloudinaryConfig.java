package Starlink.starlink_access.config;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        final Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "daxsqhinw");
        config.put("api_key", "289912319426289");
        config.put("api_secret", "Rb-h7DHoJrDbw-eMpEaB2WFL8w0");
        return new Cloudinary(config);
    }
}
