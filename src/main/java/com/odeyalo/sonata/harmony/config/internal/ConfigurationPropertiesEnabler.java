package com.odeyalo.sonata.harmony.config.internal;

import com.odeyalo.sonata.harmony.support.properties.AwsProperties;
import com.odeyalo.sonata.harmony.support.properties.FileUploadProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Enable the configuration properties classes
 */
@EnableConfigurationProperties(value = {
        FileUploadProperties.class,
        AwsProperties.class
})
@Configuration
public class ConfigurationPropertiesEnabler {
}
