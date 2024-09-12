package mockProject.team3.Vaccination_20.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
        	.info(new Info()
            .title("Black Myth Vaccination endpoint backend testing Swagger! welcome!")
            .version("1.0")
            .description("Spring Boot mock project of team 3 about vaccination"));
    }
}
