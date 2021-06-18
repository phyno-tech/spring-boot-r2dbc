package ke.co.phyno.learn.r2dbc.config.properties.r2dbc;

import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@EncryptablePropertySource(value = {"classpath:persistence.properties"}, ignoreResourceNotFound = true)
public class PersistenceProperties {
    @Value("${DB_HOST}")
    private String host;

    @Value("${DB_PORT}")
    private int port;

    @Value("${DB_NAME}")
    private String database;

    @Value("${DB_SCHEMA}")
    private String schema;

    @Value("${DB_USERNAME}")
    private String username;

    @Value("${DB_PASSWORD}")
    private String password;

    /* pool */
    @Value("${ke.co.phyno.learn.r2dbc.config.properties.r2dbc.pool.name}")
    private String poolName;

    @Value("${ke.co.phyno.learn.r2dbc.config.properties.r2dbc.pool.timezone}")
    private String poolTimezone;

    @Value("${DB_MAX_CONNECTIONS}")
    private int poolMaxConnections;

    @Value("${ke.co.phyno.learn.r2dbc.config.properties.r2dbc.pool.max.idle.time}")
    private long poolMaxIdleTime;

    @Value("${ke.co.phyno.learn.r2dbc.config.properties.r2dbc.pool.max.connection.time}")
    private long poolMaxConnectionTime;

    @Value("${ke.co.phyno.learn.r2dbc.config.properties.r2dbc.pool.max.acquire.time}")
    private long poolMaxAcquireTime;

    @Value("${ke.co.phyno.learn.r2dbc.config.properties.r2dbc.pool.max.lifetime}")
    private long poolMaxLifetime;
}
