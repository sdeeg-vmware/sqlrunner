import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.jdbc.core.JdbcTemplate
import org.slf4j.LoggerFactory
import javax.sql.DataSource

@Configuration
@Profile("second-template")
public class SQLRunnerConfiguration {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Bean
    public fun secondH2JDBCTemplate(): JdbcTemplate {
        
        logger.info("Creating second H2 JdbcTemplate")
        return JdbcTemplate( secondH2() )
    }

    @Bean
    public fun secondH2(): DataSource {
        logger.info("Creating second H2 DataSource")
        val dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.url("jdbc:h2:~/test");
        dataSourceBuilder.username("sa");
        dataSourceBuilder.password("");
        return dataSourceBuilder.build();
    }

    // @Bean
    // public fun postgresDataSource(): DataSource {
    //     logger.info("Creating second DataSource")
    //     val dataSourceBuilder = DataSourceBuilder.create();
    //     dataSourceBuilder.driverClassName("org.postgresql.Driver");
    //     dataSourceBuilder.url("jdbc:postgresql://some_host/database");
    //     dataSourceBuilder.username("SA");
    //     dataSourceBuilder.password("");
    //     return dataSourceBuilder.build();
    // }

}
