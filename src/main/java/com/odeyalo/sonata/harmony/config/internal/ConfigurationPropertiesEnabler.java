package com.odeyalo.sonata.harmony.config.internal;

import com.odeyalo.sonata.harmony.support.properties.FileUploadProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Enable the configuration properties classes
 */
@EnableConfigurationProperties(FileUploadProperties.class)
@Configuration
public class ConfigurationPropertiesEnabler {
}
