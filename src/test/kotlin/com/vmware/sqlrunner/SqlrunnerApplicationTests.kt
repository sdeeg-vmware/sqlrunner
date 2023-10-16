package com.vmware.sqlrunner

// import org.junit.Test
// import org.junit.runner.RunWith
// import org.springframework.boot.test.context.SpringBootTest
// import org.springframework.test.context.junit4.SpringRunner

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.boot.test.context.SpringBootTest

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;

// @RunWith(SpringRunner::class)
@SpringBootTest()
class SqlrunnerApplicationTests {

	@Test
	fun contextLoads() {
	}

}
