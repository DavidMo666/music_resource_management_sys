package com.g12.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("jwt")
@Data
public class JwtProperty {
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;
}
