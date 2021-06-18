package ke.co.phyno.learn.r2dbc.config.r2dbc;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Option;
import ke.co.phyno.learn.r2dbc.config.properties.r2dbc.PersistenceProperties;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import java.time.Clock;
import java.time.Duration;
import java.time.ZoneId;
import java.util.logging.Level;

@Log
@Configuration
@EnableR2dbcRepositories
@EnableR2dbcAuditing
public class PersistenceConfigurer extends AbstractR2dbcConfiguration {
    @Autowired
    private PersistenceProperties persistenceProperties;

    @Bean
    @Primary
    @Override
    public ConnectionFactory connectionFactory() {
        ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.DRIVER,"postgresql")
                .option(ConnectionFactoryOptions.HOST, this.persistenceProperties.getHost())
                .option(ConnectionFactoryOptions.PORT, this.persistenceProperties.getPort())
                .option(ConnectionFactoryOptions.USER, this.persistenceProperties.getUsername())
                .option(ConnectionFactoryOptions.PASSWORD, this.persistenceProperties.getPassword())
                .option(ConnectionFactoryOptions.DATABASE, this.persistenceProperties.getDatabase())
                .option(Option.valueOf("schema"), this.persistenceProperties.getSchema())
                .build();
        log.log(Level.INFO, String.format("Persistence connection factory options [ %s ]", options));
        return ConnectionFactories.get(options);
    }

    @Bean
    @Primary
    public ConnectionPool connectionPool() {
        return new ConnectionPool(ConnectionPoolConfiguration.builder(connectionFactory())
                .acquireRetry(3)
                .backgroundEvictionInterval(Duration.ZERO)
                .clock(Clock.system(ZoneId.of(this.persistenceProperties.getPoolTimezone())))
                .initialSize(2)
                .maxSize(this.persistenceProperties.getPoolMaxConnections())
                .maxIdleTime(Duration.ofMillis(this.persistenceProperties.getPoolMaxIdleTime()))
                .maxCreateConnectionTime(Duration.ofMillis(this.persistenceProperties.getPoolMaxConnectionTime()))
                .maxAcquireTime(Duration.ofMillis(this.persistenceProperties.getPoolMaxAcquireTime()))
                .maxLifeTime(Duration.ofMillis(this.persistenceProperties.getPoolMaxLifetime()))
                .name(this.persistenceProperties.getPoolName())
                .build());
    }
}
