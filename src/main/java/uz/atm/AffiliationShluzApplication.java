package uz.atm;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import uz.atm.properties.JusticeAPiProperties;
import uz.atm.properties.JusticeRequestProperties;
import uz.atm.properties.RabbitMQProperties;

@OpenAPIDefinition
@EnableConfigurationProperties(value = {
        JusticeAPiProperties.class,
        JusticeRequestProperties.class,
        RabbitMQProperties.class
})
@SpringBootApplication
public class AffiliationShluzApplication {

    public static void main(String[] args) {
        SpringApplication.run(AffiliationShluzApplication.class, args);
    }

}
