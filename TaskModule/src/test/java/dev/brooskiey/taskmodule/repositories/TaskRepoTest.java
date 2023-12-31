package dev.brooskiey.taskmodule.repositories;

import dev.brooskiey.taskmodule.TaskModuleApplication;
import dev.brooskiey.taskmodule.models.RecurrTask;
import dev.brooskiey.taskmodule.models.Task;
import dev.brooskiey.taskmodule.models.TaskStatus;
import dev.brooskiey.taskmodule.models.TaskType;
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
class TaskRepoTest {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private RecurrTaskRepo recurrRepo;

    @Autowired
    private TaskStatusRepo statusRepo;

    @Autowired
    private TaskTypeRepo typeRepo;

    Task task;
    RecurrTask recurrTask;
    TaskStatus status;
    TaskType type;

    // Setup for the tests
    @BeforeAll
    void setup() {
        RecurrTask recurTask = new RecurrTask(1, "WEEKLY", "TUESDAY", LocalDate.parse("2023-12-28"));
        recurrTask = recurrRepo.save(recurTask);
        Assertions.assertNotEquals(0, recurrTask.getId());

        TaskStatus taskStatus = new TaskStatus(1, "not started");
        status = statusRepo.save(taskStatus);
        Assertions.assertNotEquals(0, status.getId());

        TaskType taskType = new TaskType(1, "Cleaning");
        type = typeRepo.save(taskType);
        Assertions.assertNotEquals(0, type.getId());

        task = new Task(0, "Test Task", type, status, recurrTask,
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
        Task taskFound = taskRepo.findById(task.getId());
        Assertions.assertNotNull(taskFound);
        Assertions.assertEquals(task.getId(), taskFound.getId());
    }

    // Get task by date
    @Test
    @Order(3)
    void getTaskByDateInitiated_success(){
        List<Task> tasks = taskRepo.findByDateInitiated(task.getDateInitiated());
        Assertions.assertTrue(tasks.size()>=1);
    }

    // Get task by recurrence id
    @Test
    @Order(4)
    void getTaskByRecurrenceId_success(){
        List<Task> tasks = taskRepo.findByRecurrenceId(task.getRecurrence().getId());
        Assertions.assertTrue(tasks.size()>=1);
    }

    // Get task by name
    @Test
    @Order(4)
    void getTaskByName_success(){
        List<Task> tasks = taskRepo.findByName(task.getName());
        Assertions.assertTrue(tasks.size()>=1);
    }

    // Get task by type
    @Test
    @Order(5)
    void getTaskByType_success() {
        List<Task> tasks = taskRepo.findByType(task.getType().getName());
        Assertions.assertTrue(tasks.size()>=1);
    }

    // Get all success
    @Test
    @Order(6)
    void getAllTasks_success() {
        List<Task> tasks = (List<Task>) taskRepo.findAll();
        Assertions.assertTrue(tasks.size()>=1);
    }

    // Update success
    @Test
    @Order(7)
    void updateTask_success() {
        TaskStatus taskStatus = new TaskStatus(2, "Completed");
        status = statusRepo.save(taskStatus);

        task.setComplete(true);
        task.setStatus(taskStatus);

        Task savedTask = taskRepo.save(task);
        Assertions.assertEquals(task.getStatus().getName(), savedTask.getStatus().getName());
        Assertions.assertTrue(savedTask.isComplete());
        Assertions.assertEquals(task.getStatus().getId(), status.getId());
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

        Assertions.assertEquals(0, ((List<Task>) taskRepo.findAll()).size());
        Assertions.assertEquals(0, ((List<RecurrTask>) recurrRepo.findAll()).size());
        Assertions.assertEquals(0, ((List<TaskStatus>) statusRepo.findAll()).size());
        Assertions.assertEquals(0, ((List<TaskType>) typeRepo.findAll()).size());

    }

}
