package ke.co.phyno.learn.r2dbc.config.security;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import ke.co.phyno.learn.r2dbc.config.properties.jasypt.JasyptProperties;
import lombok.extern.java.Log;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log
@Configuration
@EnableEncryptableProperties
public class JasyptSecurityConfigurer {
    @Autowired
    private JasyptProperties jasyptProperties;

    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(this.jasyptProperties.getPassword());
        config.setAlgorithm(this.jasyptProperties.getAlgorithm());
        config.setKeyObtentionIterations(this.jasyptProperties.getKeyObtentionIterations());
        config.setPoolSize(this.jasyptProperties.getPoolSize());
        config.setProviderName(this.jasyptProperties.getProviderName());
        config.setSaltGeneratorClassName(this.jasyptProperties.getGeneratorClassName());
        config.setStringOutputType(this.jasyptProperties.getOutputType());
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(config);
        return encryptor;
    }
}
