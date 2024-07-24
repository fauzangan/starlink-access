package Starlink.starlink_access.config;
// Import the required packages

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary(){
        final Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "daxsqhinw");
        config.put("api_key", "289912319426289");
        config.put("secret_key", "Rb-h7DHoJrDbw-eMpEaB2WFL8w");
        return new Cloudinary(config);
    }
}
