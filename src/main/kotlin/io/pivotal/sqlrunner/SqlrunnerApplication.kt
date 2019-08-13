package io.pivotal.sqlrunner

import org.springframework.boot.autoconfigure.SpringBootApplication
//import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
import org.springframework.boot.runApplication

@SpringBootApplication
//@EnableOAuth2Sso
class SqlrunnerApplication

fun main(args: Array<String>) {
	runApplication<SqlrunnerApplication>(*args)
}
