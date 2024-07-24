package Starlink.starlink_access.config;

import com.midtrans.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MidtransConfig {

    @Value("${midtrans.serverKey}")
    private String serverKey;

//    @Value("${midtrans.isProduction")
    private Boolean isProduction = false;

    @Bean
    public Config midtransConfiguration() {
        Config config = Config.builder()
                .setServerKey(serverKey)
                .setIsProduction(isProduction)
                .build();

        return config;
    }
}
