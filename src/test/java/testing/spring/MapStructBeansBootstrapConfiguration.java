package testing.spring;

import com.odeyalo.sonata.harmony.support.converter.ArtistEntityConverter;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackageClasses = ArtistEntityConverter.class)
public class MapStructBeansBootstrapConfiguration {
}
