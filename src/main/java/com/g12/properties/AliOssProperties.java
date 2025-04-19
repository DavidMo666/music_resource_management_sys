package com.g12.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "alioss")
public class AliOssProperties {

    private String endPoint;
    private String accessKeyId;
    private String accessKeySecrete;
    private String bucketName;
}
