package tn.esprit.apigateway;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public GroupedOpenApi cartApi() {
        return GroupedOpenApi.builder()
                .group("Cart Service")
                .pathsToMatch("/cart/**")
                .build();
    }

    @Bean
    public GroupedOpenApi orderApi() {
        return GroupedOpenApi.builder()
                .group("Order Service")
                .pathsToMatch("/orders/**")
                .build();
    }
}
