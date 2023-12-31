package dev.brooskiey.taskmodule.repositories;

import dev.brooskiey.taskmodule.TaskModuleApplication;
import dev.brooskiey.taskmodule.models.*;
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
class TaskEntityRepoTest {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private RecurrRepo recurrRepo;

    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private TypeRepo typeRepo;

    @Autowired
    private CategoryRepo categoryRepo;


    TaskEntity task;
    RecurrenceEntity recurrence;
    StatusEntity status;
    TypeEntity type;
    CategoryEntity category;

    // Setup for the tests
    @BeforeAll
    void setup() {
        CategoryEntity category = new CategoryEntity(1, "Cleaning");
        this.category = categoryRepo.save(category);
        RecurrenceEntity recurrence = new RecurrenceEntity(1, category, "TUESDAY", LocalDate.parse("2023-12-28"));
        this.recurrence = recurrRepo.save(recurrence);
        Assertions.assertNotEquals(0, recurrence.getId());

        StatusEntity status = new StatusEntity(1, "not started");
        this.status = statusRepo.save(status);
        Assertions.assertNotEquals(0, this.status.getId());

        TypeEntity type = new TypeEntity(1, "Cleaning");
        this.type = typeRepo.save(type);
        Assertions.assertNotEquals(0, this.type.getId());

        task = new TaskEntity(0, "Test TaskEntity", this.type, this.status, recurrence,
                LocalDate.parse("2023-12-28"), null, false);
    }

    // Save success
    @Test
    @Order(1)
    void saveTask_success() {
        task = taskRepo.save(task);
        Assertions.assertNotEquals(0, task.getId());
    }

    // Get task by id success
    @Test
    @Order(2)
    void getTaskById_success() {
        TaskEntity taskFound = taskRepo.findById(task.getId());
        Assertions.assertNotNull(taskFound);
        Assertions.assertEquals(task.getId(), taskFound.getId());
    }

    // Get task by date
    @Test
    @Order(3)
    void getTaskByDateInitiated_success(){
        List<TaskEntity> taskEntities = taskRepo.findByDateInitiated(task.getDateInitiated());
        Assertions.assertTrue(taskEntities.size()>=1);
    }

    // Get task by recurrence id
    @Test
    @Order(4)
    void getTaskByRecurrenceId_success(){
        List<TaskEntity> taskEntities = taskRepo.findByRecurrenceId(task.getRecurrence().getId());
        Assertions.assertTrue(taskEntities.size()>=1);
    }

    // Get task by name
    @Test
    @Order(4)
    void getTaskByName_success(){
        List<TaskEntity> taskEntities = taskRepo.findByName(task.getName());
        Assertions.assertTrue(taskEntities.size()>=1);
    }

    // Get task by type
    @Test
    @Order(5)
    void getTaskByType_success() {
        List<TaskEntity> taskEntities = taskRepo.findByType(task.getType().getName());
        Assertions.assertTrue(taskEntities.size()>=1);
    }

    // Get all success
    @Test
    @Order(6)
    void getAllTasks_success() {
        List<TaskEntity> taskEntities = (List<TaskEntity>) taskRepo.findAll();
        Assertions.assertTrue(taskEntities.size()>=1);
    }

    // Update success
    @Test
    @Order(7)
    void updateTask_success() {
        StatusEntity status = new StatusEntity(2, "Completed");
        this.status = statusRepo.save(status);

        task.setComplete(true);
        task.setStatus(status);

        TaskEntity savedTaskEntity = taskRepo.save(task);
        Assertions.assertEquals(task.getStatus().getName(), savedTaskEntity.getStatus().getName());
        Assertions.assertTrue(savedTaskEntity.isComplete());
        Assertions.assertEquals(task.getStatus().getId(), this.status.getId());
    }

    // delete success
    @Test
    @Order(8)
    void deleteTask_success() {
        taskRepo.deleteById(task.getId());
        Assertions.assertNull(taskRepo.findById(task.getId()));
    }

    // Teardown
    @AfterAll
    void teardown() {
        taskRepo.deleteAll();
        recurrRepo.deleteAll();
        statusRepo.deleteAll();
        typeRepo.deleteAll();

        Assertions.assertEquals(0, ((List<TaskEntity>) taskRepo.findAll()).size());
        Assertions.assertEquals(0, ((List<RecurrenceEntity>) recurrRepo.findAll()).size());
        Assertions.assertEquals(0, ((List<StatusEntity>) statusRepo.findAll()).size());
        Assertions.assertEquals(0, ((List<TypeEntity>) typeRepo.findAll()).size());

    }

}
