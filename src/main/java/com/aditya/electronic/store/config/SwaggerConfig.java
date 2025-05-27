package com.aditya.electronic.store.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "scheme1",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@OpenAPIDefinition(
        info = @Info(
                title = "Electronic Store API",
                description = "This is Backend of Electronic Store",
                version = "1.0v",
                contact = @Contact(
                        name = "Aditya Kumar",
                        email = "adityasingh62065@gmail.com",
                        url = "https://github.com/Adityakumarsinghstm"
                ),
                license = @License(
                        name = "OPEN License",
                        url = "https://github.com/Adityakumarsinghstm"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "This is the External Docs",
                url = "https://github.com/Adityakumarsinghstm/E-Store/blob/main/README.md"
        )
)
public class SwaggerConfig {


//    @Bean
//    public OpenAPI springShopOpenAPI() {
//
//        String schemeName = "bearerScheme";
//        return new OpenAPI()
//                .addSecurityItem(new SecurityRequirement()
//                        .addList(schemeName))
//                .components(new Components()
//                        .addSecuritySchemes(schemeName,new SecurityScheme()
//                                .name(schemeName)
//                                .type(SecurityScheme.Type.HTTP)
//                                .bearerFormat("JWT")
//                                .scheme("bearer"))
//
//                )
//                .info(new Info().title("Electronic Store API")
//                        .description("this is electronic store api developed by aditya")
//                        .version("v1.0")
//                        .contact(new Contact().name("Aditya Kumar").email("adityasingh62065@gmail.com"))
//                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
//                .externalDocs(new ExternalDocumentation()
//                        .description("SpringShop Wiki Documentation")
//                        .url("https://springshop.wiki.github.org/docs"));
//    }
}
