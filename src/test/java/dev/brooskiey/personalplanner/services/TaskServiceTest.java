package dev.brooskiey.personalplanner.services;

import dev.brooskiey.personalplanner.PersonalPlannerBackendApplication;
import dev.brooskiey.personalplanner.exceptions.FailedToCreateTask;
import dev.brooskiey.personalplanner.exceptions.FailedToGetTask;
import dev.brooskiey.personalplanner.exceptions.FailedToUpdateTask;
import dev.brooskiey.personalplanner.models.RecurrTask;
import dev.brooskiey.personalplanner.models.Task;
import dev.brooskiey.personalplanner.models.TaskStatus;
import dev.brooskiey.personalplanner.models.TaskType;
import dev.brooskiey.personalplanner.repositories.TaskRepo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest(classes= PersonalPlannerBackendApplication.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    TaskRepo taskRepo;

    @InjectMocks
    TaskService service;

    Task task;
    Task savedTask;

    // Set up
    @BeforeAll
    void setup() {
        RecurrTask recurrTask = new RecurrTask(1, "WEEKLY", "TUESDAY", new Date(System.currentTimeMillis()));

        TaskStatus status = new TaskStatus(1, "not started");
        TaskType type = new TaskType(1, "Cleaning");
        task = new Task(0, "Test Task", type, status, recurrTask,
                new Date(System.currentTimeMillis()), null, false);
        savedTask = new Task(1, "Test Task", type, status, recurrTask,
                new Date(System.currentTimeMillis()), null, false);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Create Task SUCCESS         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Success: correct
    @Order(1)
    @Test
    void createTask_Correct_Success() throws FailedToCreateTask {
        when(taskRepo.save(task)).thenReturn(savedTask);

        Task newTask = service.createTask(task);
        Assertions.assertNotEquals(0, newTask.getId());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Create Task FAILED         ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Failed: null type
    @Order(2)
    @Test
    void createTask_NullType_Failure() {
        Task failTask = new Task(0,
                "Test Task",
                null,
                new TaskStatus(1, "not started"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", new Date(System.currentTimeMillis())),
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.createTask(failTask),
                "Failed to create: task is invalid");
    }

    // Failed: Type doesn't exist
    @Order(3)
    @Test
    void createTask_TypeNotExist_Failure() {
        Task failTask = new Task(0,
                "Test Task",
                new TaskType(3, "TEST"),
                new TaskStatus(1, "not started"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", new Date(System.currentTimeMillis())),
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.createTask(failTask),
                "Failed to create: task is invalid");
    }

    // Failed: null name
    @Order(4)
    @Test
    void createTask_NullName_Failure() {
        Task failTask = new Task(0,
                null,
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", new Date(System.currentTimeMillis())),
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.createTask(failTask),
                "Failed to create: task is invalid");
    }

    // Failed: empty String name
    @Order(5)
    @Test
    void createTask_EmptyStringName_Failure() {
        Task failTask = new Task(0,
                "",
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", new Date(System.currentTimeMillis())),
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.createTask(failTask),
                "Failed to create: task is invalid");
    }

    // Failed: null status
    @Order(6)
    @Test
    void createTask_NullStatus_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                null,
                new RecurrTask(1, "WEEKLY", "TUESDAY", new Date(System.currentTimeMillis())),
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.createTask(failTask),
                "Failed to create: task is invalid");
    }

    // Failed: Status doesn't exist
    @Order(7)
    @Test
    void createTask_StatusNotExist_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                new TaskStatus(3, "TEST"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", new Date(System.currentTimeMillis())),
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.createTask(failTask),
                "Failed to create: task is invalid");
    }

    // Failed: null recurrence
    @Order(8)
    @Test
    void createTask_NullRecurrence_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                null,
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.createTask(failTask),
                "Failed to create: task is invalid");
    }

    // Failed: Recurrence doesn't exist
    @Order(9)
    @Test
    void createTask_RecurrenceNotExist_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                new RecurrTask(3, "TEST", "TEST", new Date(System.currentTimeMillis())),
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.createTask(failTask),
                "Failed to create: task is invalid");
    }

    // Failed: null date initiated
    @Order(10)
    @Test
    void createTask_NullInitiateDate_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", new Date(System.currentTimeMillis())),
                null,
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.createTask(failTask),
                "Failed to create: task is invalid");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get Task By Id SUCCESS         ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Get Task by Id Success
    @Order(11)
    @Test
    void getTaskById_Correct_Success() {
        when(taskRepo.findById(1)).thenReturn(savedTask);

        Assertions.assertDoesNotThrow(() -> service.getTaskById(1));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get Task By Id FAILED         /////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Failed: id is 0
    @Order(12)
    @Test
    void getTaskById_IdIsZero_Failure() {
        Assertions.assertThrows(
                FailedToGetTask.class,
                () -> service.getTaskById(0),
                "Failed to get task: id is bad");
    }

    // Failed: id doesn't exist
    @Order(13)
    @Test
    void getTaskById_IdDoesNotExist_Failure() {
        when(taskRepo.findById(1)).thenReturn(null);
        Assertions.assertThrows(
                FailedToGetTask.class,
                () -> service.getTaskById(1),
                "Failed to get task: task does not exist");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get Tasks By Date SUCCESS         /////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Success: 1 task
    @Order(14)
    @Test
    void getTaskByDate_OneTasks_Success() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(savedTask);

        when(taskRepo.findByDateInitiated(task.getDateInitiated())).thenReturn(tasks);

        List<Task> foundTasks = service.getTasksByDate(task.getDateInitiated());

        Assertions.assertNotEquals(0, foundTasks.size());
    }

    // Success: 0 tasks
    @Order(15)
    @Test
    void getTaskByDate_ZeroTask_Success() {
        List<Task> tasks = new ArrayList<>();

        when(taskRepo.findByDateInitiated(task.getDateInitiated())).thenReturn(tasks);

        List<Task> foundTasks = service.getTasksByDate(task.getDateInitiated());

        Assertions.assertEquals(0, foundTasks.size());
    }

    // Success: does not throw error
    @Order(16)
    @Test
    void getTaskByDate_DoesNotThrowError_Success() {
        List<Task> tasks = new ArrayList<>();

        when(taskRepo.findByDateInitiated(task.getDateInitiated())).thenReturn(tasks);

        Assertions.assertDoesNotThrow(() -> service.getTasksByDate(task.getDateInitiated()));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get Tasks By Date FAILED         //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Failed: Date is invalid
    @Order(17)
    @Test
    void getTaskByDate_InvalidDate_Failure() {
        task.setDateInitiated(new Date(0));

        Assertions.assertThrows(FailedToGetTask.class,
                () -> service.getTasksByDate(task.getDateInitiated()),
                "Failed to get task: date is invalid");
    }

    // Failed: Date is null
    @Order(18)
    @Test
    void getTaskByDate_NullDate_Failure() {
        task.setDateInitiated(null);

        Assertions.assertThrows(FailedToGetTask.class,
                () -> service.getTasksByDate(task.getDateInitiated()),
                "Failed to get task: date is null");
    }

    // Failed: Date throws error
    @Order(19)
    @Test
    void getTaskByDate_DateThrowError_Failure() {
        task.setDateInitiated(null);

        Assertions.assertThrows(FailedToGetTask.class,
                () -> service.getTasksByDate(task.getDateInitiated()),
                "Failed to get task: Date is invalid");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get Tasks By Type SUCCESS         /////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Success: Get Tasks by Type
    @Order(20)
    @Test
    void getTasksByType_Correct_Success() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(savedTask);

        when(taskRepo.findByType(task.getType().getName())).thenReturn(tasks);

        List<Task> foundTasks = service.getTasksByType(task.getType().getName());

        Assertions.assertNotEquals(0, foundTasks.size());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get Tasks By Type FAILED         //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Failed: Type name doesn't exist
    @Order(21)
    @Test
    void getTaskByType_NameNotInTable_Failure() {
        task.setType(new TaskType(2,"TASK TEST FAIL"));

        Assertions.assertThrows(FailedToGetTask.class,
                () -> service.getTasksByType(task.getType().getName()),
                "Failed to get task: Type does not exist");
    }

    // Failed: Type is null
    @Order(22)
    @Test
    void getTaskByType_IsNull_Failure() {
        task.setType(null);

        Assertions.assertThrows(FailedToGetTask.class,
                () -> service.getTasksByType(task.getType().getName()),
                "Failed to get task: Type is null");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get All Tasks SUCCESS         /////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Success: 1+ tasks
    @Order(23)
    @Test
    void getAllTasks_OneOrMore_Success() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(savedTask);

        when(taskRepo.findAll()).thenReturn(tasks);

        List<Task> allTasks = service.getAllTasks();

        Assertions.assertNotEquals(0, allTasks.size());
    }

    // Success: no tasks
    @Order(24)
    @Test
    void getAllTasks_NoTasks_Success() {
        List<Task> tasks = new ArrayList<>();

        when(taskRepo.findAll()).thenReturn(tasks);

        List<Task> allTasks = service.getAllTasks();

        Assertions.assertEquals(0, allTasks.size());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Update Tasks SUCCESS         //////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Success: Correct Status
    @Order(25)
    @Test
    void updateTaskStatus_Correct_Success() {
        task.setStatus(new TaskStatus(2, "Completed"));
        savedTask.setStatus(new TaskStatus(2, "Completed"));

        when(taskRepo.save(task)).thenReturn(savedTask);

        Task newTask = service.updateTaskStatus(task);
        Assertions.assertNotEquals(task.getStatus().getName(), newTask.getStatus().getName());
    }

    // Success: Correct Recurrence
    @Order(26)
    @Test
    void updateTaskRecurrence_Correct_Success() {
        task.setRecurrence(new RecurrTask(2, "WEEKLY", "TUESDAY", new Date(System.currentTimeMillis())));
        savedTask.setRecurrence(new RecurrTask(2, "WEEKLY", "TUESDAY", new Date(System.currentTimeMillis())));

        when(taskRepo.save(task)).thenReturn(savedTask);

        Task newTask = service.updateTaskRecurrence(task);
        Assertions.assertNotEquals(task.getRecurrence().getRecurrence(), newTask.getRecurrence().getRecurrence());
    }

    // Success: Correct Type
    @Order(27)
    @Test
    void updateTaskType_Correct_Success() {
        task.setType(new TaskType(2, "Cleansing"));
        savedTask.setType(new TaskType(2, "Appointment"));

        when(taskRepo.save(task)).thenReturn(savedTask);

        Task newTask = service.updateTaskType(task);
        Assertions.assertNotEquals(task.getType().getName(), newTask.getType().getName());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Update Status Task FAILED         /////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Failed: Type null
    @Order(28)
    @Test
    void updateTaskStatus_NullType_Failure() {
        Task failTask = new Task(0,
                "Test Task",
                null,
                new TaskStatus(1, "not started"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", new Date(System.currentTimeMillis())),
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.updateTaskStatus(failTask),
                "Failed to update: type is null");
    }

    // Failed: Type doesn't exist
    @Order(29)
    @Test
    void updateTaskStatus_TypeNotExist_Failure() {
        Task failTask = new Task(0,
                "Test Task",
                new TaskType(3, "TEST"),
                new TaskStatus(1, "not started"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", new Date(System.currentTimeMillis())),
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.updateTaskStatus(failTask),
                "Failed to create: type is null");
    }

    // Failed: Name null
    @Order(30)
    @Test
    void updateTaskStatus_NullName_Failure() {
        Task failTask = new Task(0,
                null,
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", new Date(System.currentTimeMillis())),
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.updateTaskStatus(failTask),
                "Failed to create: name is null");
    }

    // Failed: Empty String Name
    @Order(31)
    @Test
    void updateTaskStatus_EmptyStringName_Failure() {
        Task failTask = new Task(0,
                "",
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", new Date(System.currentTimeMillis())),
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.updateTaskStatus(failTask),
                "Failed to create: name is empty");
    }

    // Failed: Status null
    @Order(32)
    @Test
    void updateTaskStatus_NullStatus_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                null,
                new RecurrTask(1, "WEEKLY", "TUESDAY", new Date(System.currentTimeMillis())),
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.updateTaskStatus(failTask),
                "Failed to create: status is null");
    }

    // Failed: Status doesn't exist
    @Order(33)
    @Test
    void updateTaskStatus_StatusNotExist_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                new TaskStatus(3, "TEST"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", new Date(System.currentTimeMillis())),
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.updateTaskStatus(failTask),
                "Failed to create: status is null");
    }

    // Failed: null recurrence
    @Order(34)
    @Test
    void updateTaskStatus_NullRecurrence_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                null,
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.updateTaskStatus(failTask),
                "Failed to create: recurrence is null");
    }

    // Failed: Recurrence doesn't exist
    @Order(35)
    @Test
    void updateTaskStatus_RecurrenceNotExist_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                new RecurrTask(3, "WEEKLY", "TEST", new Date(System.currentTimeMillis())),
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.updateTaskStatus(failTask),
                "Failed to create: recurrence is null");
    }

    // Failed: null date initiated
    @Order(36)
    @Test
    void updateTaskStatus_NullInitiateDate_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", new Date(System.currentTimeMillis())),
                null,
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.updateTaskStatus(failTask),
                "Failed to create: Date is null");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Update Type Task FAILED         ///////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // Failed: null type
    @Order(37)
    @Test
    void updateTaskType_NullType_Failure() {
        Task failTask = new Task(0,
                "Test Task",
                null,
                new TaskStatus(1, "not started"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", new Date(System.currentTimeMillis())),
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.updateTaskType(failTask),
                "Failed to create: type is null");
    }

    // Failed: Type doesn't exist
    @Order(38)
    @Test
    void updateTaskType_TypeNotExist_Failure() {
        Task failTask = new Task(0,
                "Test Task",
                new TaskType(3, "TEST"),
                new TaskStatus(1, "not started"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", new Date(System.currentTimeMillis())),
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.updateTaskType(failTask),
                "Failed to create: type is null");
    }

    // Failed: null name
    @Order(39)
    @Test
    void updateTaskType_NullName_Failure() {
        Task failTask = new Task(0,
                null,
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", new Date(System.currentTimeMillis())),
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.updateTaskType(failTask),
                "Failed to create: name is null");
    }

    // Failed: empty String name
    @Order(40)
    @Test
    void updateTaskType_EmptyStringName_Failure() {
        Task failTask = new Task(0,
                "",
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", new Date(System.currentTimeMillis())),
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.updateTaskType(failTask),
                "Failed to create: name is empty");
    }

    // Failed: null status
    @Order(41)
    @Test
    void updateTaskType_NullStatus_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                null,
                new RecurrTask(1, "WEEKLY", "TUESDAY", new Date(System.currentTimeMillis())),
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.updateTaskType(failTask),
                "Failed to create: status is null");
    }

    // Failed: Status doesn't exist
    @Order(42)
    @Test
    void updateTaskType_StatusNotExist_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                new TaskStatus(3, "TEST"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", new Date(System.currentTimeMillis())),
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.updateTaskType(failTask),
                "Failed to create: status is null");
    }

    // Failed: null recurrence
    @Order(43)
    @Test
    void updateTaskType_NullRecurrence_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                null,
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.updateTaskType(failTask),
                "Failed to create: recurrence is null");
    }

    // Failed: Recurrence doesn't exist
    @Order(44)
    @Test
    void updateTaskType_RecurrenceNotExist_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                new RecurrTask(3, "WEEKLY", "TEST", new Date(System.currentTimeMillis())),
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.updateTaskType(failTask),
                "Failed to create: recurrence is null");
    }

    // Failed: null date initiated
    @Order(45)
    @Test
    void updateTaskType_NullInitiateDate_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", new Date(System.currentTimeMillis())),
                null,
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.updateTaskType(failTask),
                "Failed to create: Date is null");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Update Recurrence Task FAILED         /////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // Failed: null type
    @Order(46)
    @Test
    void updateTaskRecurrence_NullType_Failure() {
        Task failTask = new Task(0,
                "Test Task",
                null,
                new TaskStatus(1, "not started"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", new Date(System.currentTimeMillis())),
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToUpdateTask.class,
                () -> service.updateTaskRecurrence(failTask),
                "Failed to update: task is invalid");
    }

    // Failed: Type doesn't exist
    @Order(47)
    @Test
    void updateTaskRecurrence_TypeNotExist_Failure() {
        Task failTask = new Task(0,
                "Test Task",
                new TaskType(3, "TEST"),
                new TaskStatus(1, "not started"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", new Date(System.currentTimeMillis())),
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToUpdateTask.class,
                () -> service.updateTaskRecurrence(failTask),
                "Failed to update: task is invalid");
    }

    // Failed: null name
    @Order(48)
    @Test
    void updateTaskRecurrence_NullName_Failure() {
        Task failTask = new Task(0,
                null,
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", new Date(System.currentTimeMillis())),
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToUpdateTask.class,
                () -> service.updateTaskRecurrence(failTask),
                "Failed to update: task is invalid");
    }

    // Failed: empty String name
    @Order(49)
    @Test
    void updateTaskRecurrence_EmptyStringName_Failure() {
        Task failTask = new Task(0,
                "",
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", new Date(System.currentTimeMillis())),
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToUpdateTask.class,
                () -> service.updateTaskRecurrence(failTask),
                "Failed to update: task is invalid");
    }

    // Failed: null status
    @Order(50)
    @Test
    void updateTaskRecurrence_NullStatus_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                null,
                new RecurrTask(1, "WEEKLY", "TUESDAY", new Date(System.currentTimeMillis())),
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToUpdateTask.class,
                () -> service.updateTaskRecurrence(failTask),
                "Failed to update: task is invalid");
    }

    // Failed: Status doesn't exist
    @Order(51)
    @Test
    void updateTaskRecurrence_StatusNotExist_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                new TaskStatus(3, "TEST"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", new Date(System.currentTimeMillis())),
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToUpdateTask.class,
                () -> service.updateTaskRecurrence(failTask),
                "Failed to update: task is invalid");
    }

    // Failed: null recurrence
    @Order(52)
    @Test
    void updateTaskRecurrence_NullRecurrence_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                null,
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToUpdateTask.class,
                () -> service.updateTaskRecurrence(failTask),
                "Failed to update: task is invalid");
    }

    // Failed: Recurrence doesn't exist
    @Order(53)
    @Test
    void updateTaskRecurrence_RecurrenceNotExist_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                new RecurrTask(3, "WEEKLY", "TEST", new Date(System.currentTimeMillis())),
                new Date(System.currentTimeMillis()),
                null,
                false);

        Assertions.assertThrows(
                FailedToUpdateTask.class,
                () -> service.updateTaskRecurrence(failTask),
                "Failed to update: task is invalid");
    }

    // Failed: null date initiated
    @Order(54)
    @Test
    void updateTaskRecurrence_NullInitiateDate_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", new Date(System.currentTimeMillis())),
                null,
                null,
                false);

        Assertions.assertThrows(
                FailedToUpdateTask.class,
                () -> service.updateTaskRecurrence(failTask),
                "Failed to update: task is invalid");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Complete Task SUCCESS         /////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Success: complete task
    @Order(55)
    @Test
    void completeTask_NullCompleteDate_Success() {
        when(taskRepo.save(task)).thenReturn(savedTask);

        Task newTask = service.complete(1);

        Assertions.assertTrue(newTask.isComplete());
        Assertions.assertNotEquals(task.getDateCompleted(), newTask.getDateCompleted());

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Complete Task FAILED         //////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Failed: complete task
    @Order(56)
    @Test
    void completeTask_ThrowError_Failure() {
        Assertions.assertThrows(FailedToUpdateTask.class,
                () -> service.complete(1),
                "Failed to complete task: task doesn't exist");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Delete Task SUCCESS         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Success: delete task
    @Order(57)
    @Test
    void deleteTask_Correct_Success() {

        String deleted = service.deleteTask(task.getId());
        Assertions.assertEquals("DELETED", deleted);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             TEARDOWN         //////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @AfterAll
    void teardown() {
        taskRepo.deleteAll();
    }
}
