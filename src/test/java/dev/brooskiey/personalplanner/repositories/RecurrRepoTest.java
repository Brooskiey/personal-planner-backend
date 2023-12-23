package dev.brooskiey.personalplanner.repositories;

import dev.brooskiey.personalplanner.PersonalPlannerBackendApplication;
import dev.brooskiey.personalplanner.models.RecurrTask;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.util.List;

@SpringBootTest(classes= PersonalPlannerBackendApplication.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RecurrRepoTest {

    @Autowired
    private RecurrTaskRepo recurrRepo;
    
    RecurrTask recurrTask;

    // Setup for the tests
    @BeforeAll
    void setup() {
        RecurrTask recurTask = new RecurrTask(1, "vacuum", new Date(System.currentTimeMillis()));
        recurrTask = recurrRepo.save(recurTask);
        Assertions.assertNotEquals(0, recurrTask.getId());
    }

    // Save success
    @Test
    @Order(1)
    void saveRecurrTask_success() {
        recurrTask = recurrRepo.save(recurrTask);
        Assertions.assertNotEquals(0, recurrTask.getId());
    }

    // Get task by id success
    @Test
    @Order(2)
    void getRecurrTaskById_success() {
        RecurrTask recurrTaskFound = recurrRepo.findById(recurrTask.getId());
        Assertions.assertNotNull(recurrTaskFound);
        Assertions.assertEquals(recurrTask.getId(), recurrTaskFound.getId());
    }

    // Get task by id success
    @Test
    @Order(3)
    void getRecurrTaskByName_success() {
        RecurrTask recurrTaskFound = recurrRepo.findByName(recurrTask.getName());
        Assertions.assertNotNull(recurrTaskFound);
        Assertions.assertEquals(recurrTask.getId(), recurrTaskFound.getId());
    }

    // Get all success
    @Test
    @Order(4)
    void getAllRecurrTask_success() {
        List<RecurrTask> tasks = (List<RecurrTask>) recurrRepo.findAll();
        Assertions.assertNotEquals(0, tasks.size());
    }

    // Update success
    @Test
    @Order(5)
    void updateRecurrTask_success() {

        recurrTask.setLastDate( new Date(System.currentTimeMillis()));

        RecurrTask savedRecurrTask = recurrRepo.save(recurrTask);
        Assertions.assertEquals(recurrTask.getLastDate(), savedRecurrTask.getLastDate());
        Assertions.assertEquals(recurrTask.getId(), savedRecurrTask.getId());
    }

    // delete success
    @Test
    @Order(6)
    void deleteTask_success() {
        recurrRepo.deleteById(recurrTask.getId());
        Assertions.assertNull(recurrRepo.findById(recurrTask.getId()));
    }

    // Teardown
    @AfterAll
    void teardown() {
        recurrRepo.deleteAll();
        Assertions.assertEquals(0, ((List<RecurrTask>) recurrRepo.findAll()).size());
    }

}
