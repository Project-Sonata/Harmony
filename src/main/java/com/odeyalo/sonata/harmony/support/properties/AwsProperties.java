package com.odeyalo.sonata.harmony.support.properties;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sonata.harmony.aws")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AwsProperties {
    Credentials credentials;
    String bucketName;
    String urlPrefix;

    @Data
    public static class Credentials {
        String key;
        String secretKey;
    }
}
