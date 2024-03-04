package com.roche.appClient.AppClient;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocConfig {

    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("AppClient by Anthony Roche")
                        .version("1.0")
                        .description("A Rest API to manage shipments to a client and the products that have that shipment, with priority validations according to the client's membership"));
    }

}
