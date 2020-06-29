package io.pivotal.sqlrunner

class SQLCommand(val ds: String, val sql: String)

class SQLRunResult(val success: Boolean = true,
                   val message: String = "",
                   val data: List<Map<String, Any>>)
