package io.pivotal.sqlrunner

import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration
import org.springframework.boot.jdbc.DataSourceBuilder
//import org.springframework.cloud.config.java.ServiceScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource

@Configuration
@Profile("create-test-data")
class SQLRunnerInitializer {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Bean
    fun applicationRunner(jdbcTemplates: Map<String,JdbcTemplate>) = ApplicationRunner {
        logger.info("Profile create-test-data active, initializing the app with data.")
        if(jdbcTemplates.size == 0) {
            //If this happens something is very wrong
            logger.error("jdbcTemplates.size=0:  Check configuration and make sure datasource is created.")
        }
        jdbcTemplates.forEach { key, jdbcTemplate ->
            if(jdbcTemplate.dataSource?.connection?.metaData?.databaseProductName == "H2") {
                logger.info("Creating tables in H2 database")
                jdbcTemplate.execute("create table my_table (id INT, my_data varchar(64))")
                jdbcTemplate.execute("insert into my_table values ( 1, 'hello, world')")
                jdbcTemplate.execute("insert into my_table values ( 2, 'foo bar baz')")
                jdbcTemplate.execute("insert into my_table values ( 3, 'No matter where you go, there you are')")
                jdbcTemplate.execute("insert into my_table values ( 42, 'life, the universe, everything')")

                jdbcTemplate.execute("create table table2 (id INT, your_data varchar(32))")
                jdbcTemplate.execute("insert into table2 values ( 1, 'rock')")
                jdbcTemplate.execute("insert into table2 values ( 2, 'roll')")
            }
            else if(jdbcTemplate.dataSource?.connection?.metaData?.databaseProductName == "MySQL") {
                logger.info("Creating tables in MySQL database")
                //TODO: add logic to see if tables already exist and create if necessary
            }
            else {
                logger.info("Did not initialize DB: ${jdbcTemplate.dataSource?.connection?.metaData?.databaseProductName}")
            }
        }
    }
}

/*
    If we're in the cloud, get the bound datasources and create a jdbcTemplate for each.
 */
@Configuration
@Profile("cloud")
class CloudInitializer() {
    @Bean
    fun jdbcTemplates(dataSourceList: List<DataSource>) = dataSourceList.map {
        it.connection.metaData.databaseProductName to JdbcTemplate(it)
    }.toMap()
}