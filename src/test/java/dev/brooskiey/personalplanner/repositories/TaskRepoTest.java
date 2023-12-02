package dev.brooskiey.personalplanner.repositories;

import dev.brooskiey.personalplanner.PersonalPlannerBackendApplication;
import dev.brooskiey.personalplanner.models.RecurrTask;
import dev.brooskiey.personalplanner.models.Task;
import dev.brooskiey.personalplanner.models.TaskStatus;
import dev.brooskiey.personalplanner.models.TaskType;
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
public class TaskRepoTest {

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
        RecurrTask recurTask = new RecurrTask(1, "vacuum", new Date(System.currentTimeMillis()));
        recurrTask = recurrRepo.save(recurTask);
        Assertions.assertNotEquals(0, recurrTask.getId());

        TaskStatus taskStatus = new TaskStatus(1, "not started");
        status = statusRepo.save(taskStatus);
        Assertions.assertNotEquals(0, status.getId());

        TaskType taskType = new TaskType(1, "Cleaning");
        type = typeRepo.save(taskType);
        Assertions.assertNotEquals(0, type.getId());

        task = new Task(0, "Test Task", type, status, recurrTask,
                new Date(System.currentTimeMillis()), null, false);
    }

    // Save success
    @Test
    @Order(1)
    public void saveTask_success() {
        task = taskRepo.save(task);
        Assertions.assertNotEquals(0, task.getId());
    }

    // Get task by id success
    @Test
    @Order(2)
    public void getTaskById_success() {
        Task taskFound = taskRepo.findById(task.getId());
        Assertions.assertNotNull(taskFound);
        Assertions.assertEquals(task.getId(), taskFound.getId());
    }

    @Test
    @Order(3)
    public void getTaskByDateInitiated_success(){
        List<Task> tasks = taskRepo.findByDateInitiated(task.getDateInitiated());
        Assertions.assertTrue(tasks.size()>=1);
    }

    @Test
    @Order(4)
    public void getTaskByRecurrenceId_success(){
        List<Task> tasks = taskRepo.findByRecurrenceId(recurrTask.getId());
        Assertions.assertTrue(tasks.size()>=1);
    }

    // Get all success
    @Test
    @Order(5)
    public void getAllTasks_success() {
        List<Task> tasks = (List<Task>) taskRepo.findAll();
        Assertions.assertTrue(tasks.size()>=1);
    }

    // Update success
    @Test
    @Order(6)
    public void updateTask_success() {
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
    @Order(7)
    public void deleteTask_success() {
        taskRepo.deleteById(task.getId());
    }

}
