package io.kuoche.post.text;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Post Text API", version = "v3", description = "Social networking service. Users of the service will be able to post text, reply comment, follow other people, and favorite text."))
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "BasicAuth", scheme = "basic")
public class PostTextApplication {
    public static void main(String[] args) {
        SpringApplication.run(PostTextApplication.class, args);
    }

}
