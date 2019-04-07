package com.demo.springbootapis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "app")
@Getter
public class AppProperties {
  private final Jwt jwt = new Jwt();
  private final Oauth2 oauth2 = new Oauth2();

  @Getter
  @Setter
  public static class Jwt {
    private String tokenSecret;
    private long tokenExpirationMsec;
  }

  @Getter
  @Setter
  public static class Oauth2 {
    private String authorizedRedirectUris;
  }
}
