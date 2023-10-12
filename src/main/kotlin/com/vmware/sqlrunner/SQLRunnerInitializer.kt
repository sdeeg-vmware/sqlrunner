package com.vmware.sqlrunner

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
    public fun H2JdbcTemplate(h2DataSource: DataSource): JdbcTemplate {
        
        logger.info("The H2 JdbcTemplate")
        return JdbcTemplate( h2DataSource )
    }

    @Bean
    public fun secondH2JdbcTemplate(h2DataSource: DataSource): JdbcTemplate {
        
        logger.info("Creating third H2 JdbcTemplate")
        return JdbcTemplate( h2DataSource )
    }

    @Bean
    public fun h2DataSource(): DataSource {
        logger.info("Creating a H2 DataSource")
        val dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.url("jdbc:h2:~/test");
        dataSourceBuilder.username("sa");
        dataSourceBuilder.password("");
        return dataSourceBuilder.build();
    }

    @Bean
    fun applicationRunner(jdbcTemplates: Map<String,JdbcTemplate>) = ApplicationRunner {
        logger.info("Profile create-test-data active, initializing the app with data.")
        if(jdbcTemplates.size == 0) {
            //If this happens something is very wrong
            logger.error("jdbcTemplates.size=0:  Check configuration and make sure datasource is created.")
        }
        jdbcTemplates.forEach{ templateKV -> //why can't I do this as key, jdbcTemplate?
            logger.info("populating DB for template ${templateKV.key}")
            if(templateKV.value.dataSource?.connection?.metaData?.databaseProductName == "H2") {
                try {
                    logger.info("Creating tables in H2 database")
                    templateKV.value.execute("create table my_table (id INT, my_data varchar(64))")
                    templateKV.value.execute("insert into my_table values ( 1, 'hello, world')")
                    templateKV.value.execute("insert into my_table values ( 2, 'foo bar baz')")
                    templateKV.value.execute("insert into my_table values ( 3, 'No matter where you go, there you are')")
                    templateKV.value.execute("insert into my_table values ( 42, 'life, the universe, everything')")
    
                    templateKV.value.execute("create table table2 (id INT, your_data varchar(32))")
                    templateKV.value.execute("insert into table2 values ( 1, 'rock')")
                    templateKV.value.execute("insert into table2 values ( 2, 'roll')")
                } catch(e: Exception) { logger.info("Got an exception") }
            }
            else if(templateKV.value.dataSource?.connection?.metaData?.databaseProductName == "MySQL") {
             logger.info("Creating tables in MySQL database")
             //TODO: add logic to see if tables already exist and create if necessary
            }
            else {
             logger.info("Did not initialize DB: ${templateKV.value.dataSource?.connection?.metaData?.databaseProductName}")
            }
        }
    }
}

/*
    If we're in the cloud, get the bound datasources and create a jdbcTemplate for each.

    I don't know what this is, so we'll comment it out for now.
 */
// @Configuration
// @Profile("cloud")
// class CloudInitializer() {
//     private val logger = LoggerFactory.getLogger(javaClass)
//     @Bean
//     fun jdbcTemplates(dataSourceList: List<DataSource>) = {
//         logger.info("Initializing a map, jdbcTemplates, with key databaseProductName and value new JdbcTemplate from the DataSource")
//         dataSourceList.map {
//             it.connection.metaData.databaseProductName to JdbcTemplate(it)
//         }.toMap()
//     }
// }
