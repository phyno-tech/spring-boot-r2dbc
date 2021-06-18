package ke.co.phyno.learn.r2dbc.config.properties.jasypt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource(value = {"classpath:jasypt.properties"})
public class JasyptProperties {
    @Value("${JASYPT_PASSWORD}")
    private String password;

    @Value("${ke.co.phyno.learn.r2dbc.config.properties.jasypt.algorithm}")
    private String algorithm;

    @Value("${ke.co.phyno.learn.r2dbc.config.properties.jasypt.key.obtention.iterations}")
    private int keyObtentionIterations;

    @Value("${ke.co.phyno.learn.r2dbc.config.properties.jasypt.pool.size}")
    private int poolSize;

    @Value("${ke.co.phyno.learn.r2dbc.config.properties.jasypt.provider.name}")
    private String providerName;

    @Value("${ke.co.phyno.learn.r2dbc.config.properties.jasypt.salt.generator.class.name}")
    private String generatorClassName;

    @Value("${ke.co.phyno.learn.r2dbc.config.properties.jasypt.output.type}")
    private String outputType;
}
