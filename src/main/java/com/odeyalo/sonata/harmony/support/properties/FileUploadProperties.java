package com.odeyalo.sonata.harmony.support.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sonata.harmony.file.upload")
@Setter
@Getter
public class FileUploadProperties {
}