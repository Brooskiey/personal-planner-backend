package dev.brooskiey.taskmodule.repositories;

import dev.brooskiey.taskmodule.TaskModuleApplication;
import dev.brooskiey.taskmodule.models.CategoryEntity;
import dev.brooskiey.taskmodule.models.RecurrenceEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(classes= TaskModuleApplication.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RecurrRepoTest {

    @Autowired
    private RecurrRepo recurrRepo;

    @Autowired
    private CategoryRepo categoryRepo;
    
    RecurrenceEntity recurrence;

    // Setup for the tests
    @BeforeAll
    void setup() {
        CategoryEntity category = new CategoryEntity(1, "Cleaning");
        categoryRepo.save(category);
        recurrence = new RecurrenceEntity(1, category, "TUESDAY", LocalDate.parse("2023-12-28"));
        recurrence = recurrRepo.save(recurrence);
        Assertions.assertNotEquals(0, recurrence.getId());
    }

    // Save success
    @Test
    @Order(1)
    void saveRecurrTask_success() {
        recurrence = recurrRepo.save(recurrence);
        Assertions.assertNotEquals(0, recurrence.getId());
    }

    // Get task by id success
    @Test
    @Order(2)
    void getRecurrTaskById_success() {
        RecurrenceEntity recurrenceFound = recurrRepo.findById(recurrence.getId());
        Assertions.assertNotNull(recurrenceFound);
        Assertions.assertEquals(recurrence.getId(), recurrenceFound.getId());
    }

    // Get task by id success
    @Test
    @Order(3)
    void getRecurrTaskByName_success() {
        RecurrenceEntity recurrenceFound = recurrRepo.findByRecurrence(recurrence.getRecurrence());
        Assertions.assertNotNull(recurrenceFound);
        Assertions.assertEquals(recurrence.getId(), recurrenceFound.getId());
    }

    // Get all success
    @Test
    @Order(4)
    void getAllRecurrTask_success() {
        List<RecurrenceEntity> tasks = (List<RecurrenceEntity>) recurrRepo.findAll();
        Assertions.assertNotEquals(0, tasks.size());
    }

    // Update success
    @Test
    @Order(5)
    void updateRecurrTask_success() {

        recurrence.setLastDate(LocalDate.parse("2023-12-28"));

        RecurrenceEntity savedRecurrenceEntity = recurrRepo.save(recurrence);
        Assertions.assertEquals(recurrence.getLastDate(), savedRecurrenceEntity.getLastDate());
        Assertions.assertEquals(recurrence.getId(), savedRecurrenceEntity.getId());
    }

    // delete success
    @Test
    @Order(6)
    void deleteTask_success() {
        recurrRepo.deleteById(recurrence.getId());
        Assertions.assertNull(recurrRepo.findById(recurrence.getId()));
    }

    // Teardown
    @AfterAll
    void teardown() {
        recurrRepo.deleteAll();
        Assertions.assertEquals(0, ((List<RecurrenceEntity>) recurrRepo.findAll()).size());
    }

}
