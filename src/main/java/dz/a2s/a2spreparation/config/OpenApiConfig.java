package dz.a2s.a2spreparation.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "A2S LogiPharm Préparation",
                version = "1.0",
                description = "API Documentation for "
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Development Server")
        }
)
public class OpenApiConfig {
}
