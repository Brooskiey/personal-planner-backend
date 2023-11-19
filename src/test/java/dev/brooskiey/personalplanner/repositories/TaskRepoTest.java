package dev.brooskiey.personalplanner.repositories;

import dev.brooskiey.personalplanner.PersonalPlannerBackendApplication;
import dev.brooskiey.personalplanner.models.RecurrTask;
import dev.brooskiey.personalplanner.models.Task;
import dev.brooskiey.personalplanner.models.TaskStatus;
import dev.brooskiey.personalplanner.models.TaskType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;

@SpringBootTest(classes= PersonalPlannerBackendApplication.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskRepoTest {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private RecurrTaskRepo recurrRepo;

    @Autowired
    private TaskStatusRepo statusRepo;

    @Autowired
    private TaskTypeRepo typeRepo;

    RecurrTask recurrTask;
    TaskStatus status;
    TaskType type;

    // Setup for the tests
    @Test
    @Order(1)
    public void setup() {
        RecurrTask recurTask = new RecurrTask(1, "vacuum", new Date(System.currentTimeMillis()));
        recurrTask = recurrRepo.save(recurTask);
        Assertions.assertNotEquals(0, recurrTask.getId());

        TaskStatus taskStatus = new TaskStatus(1, "not started");
        status = statusRepo.save(taskStatus);
        Assertions.assertNotEquals(0, status.getId());

        TaskType taskType = new TaskType(1, "Cleaning");
        type = typeRepo.save(taskType);
        Assertions.assertNotEquals(0, type.getId());
    }

    // Save success
    @Test
    @Order(2)
    public void saveTask_success() {
        Task task = new Task(0, "Test Task", type, status, recurrTask,
                new Date(System.currentTimeMillis()), null, false);
        task = taskRepo.save(task);
        Assertions.assertNotEquals(0, task.getId());
    }

    // Get success
    @Test
    @Order(3)
    public void getTask_success() {

    }

    // Get all success
    @Test
    @Order(4)
    public void getAllTasks_success() {

    }

    // Update success
    @Test
    @Order(5)
    public void updateTask_success() {

    }

    // Update failed
    @Test
    @Order(6)
    public void updateTask_fail() {

    }

    // Delete success
    @Test
    @Order(7)
    public void deleteTask_success() {

    }

}
