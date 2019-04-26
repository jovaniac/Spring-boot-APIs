package com.demo.springbootapis.config;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import io.swagger.models.auth.In;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
 * URL: http://localhost:8080/swagger-ui.html PS: JWT token need be added as
 * "Bearer eyJhbGciOiJIUzUxMiJ9.eyJz...."
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket jsonApi() {
    return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
        .securitySchemes(Collections.singletonList(
            new ApiKey("Authorization", HttpHeaders.AUTHORIZATION, In.HEADER.name())))
        .securityContexts(Collections.singletonList(SecurityContext.builder()
            .securityReferences(Collections.singletonList(SecurityReference.builder()
                .reference("Authorization").scopes(new AuthorizationScope[0]).build()))
            .build()))
        .select().apis(RequestHandlerSelectors.any()).paths(paths()).build();
  }

  // Describe your apis
  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("Swagger APIs")
        .description("This page lists all the rest apis for Spring-boot-apis App. " + "<br><br>"
            + "To get Jwt Token: make a POST call on $domain:8080/login (Development) or https://www.kaihetech.com/api/login (Cloud) with username=test, password=12345."
            + "<br>" + "Eg: localhost:8080/login" + "<br><br>"
            + "Then enter the Bearer + the token into Authorize section." + "<br>"
            + "Eg: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyIiwidXNlcm5hbWUiOiJ0ZXN0MSI...")
        .version("0.0.1-SNAPSHOT").build();
  }

  // Only select apis that matches the given Predicates.
  private Predicate<String> paths() {
    // Match all paths except /error
    return Predicates.and(PathSelectors.regex("/.*"),
        Predicates.not(PathSelectors.regex("/error.*")));
  }
}
