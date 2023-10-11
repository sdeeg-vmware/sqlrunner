package com.vmware.sqlrunner

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DataSourceUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.HashMap
import java.sql.SQLException
import java.sql.ResultSet
import java.util.ArrayList
import javax.sql.DataSource

@RestController
class SQLRunnerAPI(val jdbcTemplateList: Map<String,JdbcTemplate>,
                   val dataSourceList: List<DataSource>,
                   val sqlService: SQLService) {

    @GetMapping("/hello")
    fun getHello() = setOf("hello, world")

    //TODO: Add parameter of template ID (or name), return the details of the JdbcTemplate
    @GetMapping("/getJdbcTemplateInfo")
    fun getDataSourceInfo():String {
        return dataSourceList.size.toString()
    }

    @GetMapping("/getJdbcTemplates")
    fun getJdbcTemplates() = jdbcTemplateList.keys

    @PostMapping("/runsql")
    fun runSQL(@RequestBody sqlCommand: SQLCommand) = sqlService.parseAndRun(sqlCommand)
}
