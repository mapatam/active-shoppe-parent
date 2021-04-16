package za.co.momentum.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "swagger.oauth2")
public class SwaggerConfig {

	private boolean enabled = true;

	private String authEndpoint;

	private String tokenEndpoint;

	private String clientId;

	private String scopeSeparator = " ";

	private String tokenName = "access_token";

}
