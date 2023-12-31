package dev.brooskiey.taskmodule;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-test.properties")
class TaskModuleApplicationTests {

}
