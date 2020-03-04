package io.pivotal.sqlrunner

import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.sql.SQLException
import java.util.ArrayList

@Service
class SQLService(val jdbcTemplateList: Map<String, JdbcTemplate>) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun parseAndRun(sqlCommand: SQLCommand):SQLRunResult {
        val jt = jdbcTemplateList.get(sqlCommand.ds)
        val emptyList = ArrayList<Map<String, Any>>(0)
        var retVal = SQLRunResult(false, "Could not find data source "+sqlCommand.ds, emptyList)

        try {
            if(jt == null) { logger.warn("No datasource with name "+sqlCommand.ds)  }
            else if(sqlCommand.sql.toLowerCase().startsWith("select")) {
                retVal = SQLRunResult(true, "ran the select", jt.queryForList(sqlCommand.sql))
            }
            else if(sqlCommand.sql.toLowerCase().startsWith("create")) {
                jt.execute(sqlCommand.sql)
                retVal = SQLRunResult(true, "Ran create statement", emptyList)
            }
            else if(sqlCommand.sql.toLowerCase().matches(Regex("(insert|update|delete).*"))) {
                val updateResultInt = jt.update(sqlCommand.sql)
                retVal = SQLRunResult(true, "Ran update statement with result"+updateResultInt, emptyList)
            }
            else { logger.warn("SQL $ command not recognized.", sqlCommand.sql) }
        } catch(ex: Exception) {
            retVal = SQLRunResult(false, ""+ex.message, emptyList)
        }

        return retVal
    }
}