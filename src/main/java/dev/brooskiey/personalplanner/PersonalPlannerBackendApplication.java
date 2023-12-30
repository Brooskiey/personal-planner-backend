package dev.brooskiey.personalplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class PersonalPlannerBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalPlannerBackendApplication.class, args);
    }

}
