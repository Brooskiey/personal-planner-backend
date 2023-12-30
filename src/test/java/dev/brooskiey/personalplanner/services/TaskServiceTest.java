package dev.brooskiey.personalplanner.services;

import dev.brooskiey.personalplanner.PersonalPlannerBackendApplication;
import dev.brooskiey.personalplanner.exceptions.*;
import dev.brooskiey.personalplanner.models.RecurrTask;
import dev.brooskiey.personalplanner.models.Task;
import dev.brooskiey.personalplanner.models.TaskStatus;
import dev.brooskiey.personalplanner.models.TaskType;
import dev.brooskiey.personalplanner.repositories.TaskRepo;
import dev.brooskiey.personalplanner.repositories.TaskStatusRepo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    @Mock
    TaskStatusRepo statusRepo;

    @InjectMocks
    TaskService service;

    Task task;
    Task savedTask;

    // Set up
    @BeforeAll
    void setup() {
        RecurrTask recurrTask = new RecurrTask(1, "WEEKLY", "TUESDAY", LocalDate.parse("2023-12-28"));

        TaskStatus status = new TaskStatus(1, "not started");
        TaskType type = new TaskType(1, "Cleaning");
        task = new Task(0, "Test Task", type, status, recurrTask,
                LocalDate.parse("2023-12-27"), null, false);
        savedTask = new Task(1, "Test Task", type, status, recurrTask,
                LocalDate.parse("2023-12-28"), null, false);

    }

    @BeforeEach
    void beforeEach() {
        RecurrTask recurrTask = new RecurrTask(1, "WEEKLY", "TUESDAY", LocalDate.parse("2023-12-28"));

        TaskStatus status = new TaskStatus(1, "not started");
        TaskType type = new TaskType(1, "Cleaning");
        task = new Task(0, "Test Task", type, status, recurrTask,
                LocalDate.parse("2023-12-27"), null, false);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Create Task SUCCESS         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Success: correct
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
    @Test
    void createTask_NullType_Failure() {
        Task failTask = new Task(0,
                "Test Task",
                null,
                new TaskStatus(1, "not started"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.createTask(failTask),
                "Failed to create: task is invalid");
    }

    // Failed: Type doesn't exist
    @Test
    void createTask_TypeNotExist_Failure() {
        Task failTask = new Task(0,
                "Test Task",
                new TaskType(3, "TEST"),
                new TaskStatus(1, "not started"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.createTask(failTask),
                "Failed to create: task is invalid");
    }

    // Failed: null name
    @Test
    void createTask_NullName_Failure() {
        Task failTask = new Task(0,
                null,
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.createTask(failTask),
                "Failed to create: task is invalid");
    }

    // Failed: empty String name
    @Test
    void createTask_EmptyStringName_Failure() {
        Task failTask = new Task(0,
                "",
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.createTask(failTask),
                "Failed to create: task is invalid");
    }

    // Failed: null status
    @Test
    void createTask_NullStatus_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                null,
                new RecurrTask(1, "WEEKLY", "TUESDAY", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.createTask(failTask),
                "Failed to create: task is invalid");
    }

    // Failed: Status doesn't exist
    @Test
    void createTask_StatusNotExist_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                new TaskStatus(3, "TEST"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.createTask(failTask),
                "Failed to create: task is invalid");
    }

    // Failed: null recurrence
    @Test
    void createTask_NullRecurrence_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                null,
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.createTask(failTask),
                "Failed to create: task is invalid");
    }

    // Failed: Recurrence doesn't exist
    @Test
    void createTask_RecurrenceNotExist_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                new RecurrTask(3, "TEST", "TEST", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.createTask(failTask),
                "Failed to create: task is invalid");
    }

    // Failed: Category doesn't exist
    @Test
    void createTask_CategoryNotExist_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                new RecurrTask(3, "TEST", "TUESDAY", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.createTask(failTask),
                "Failed to create: task is invalid");
    }

    // Failed: null date initiated
    @Test
    void createTask_NullInitiateDate_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", LocalDate.parse("2023-12-28")),
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
    @Test
    void getTaskById_Correct_Success() {
        when(taskRepo.findById(1)).thenReturn(savedTask);

        Assertions.assertDoesNotThrow(() -> service.getTaskById(1));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get Task By Id FAILED         /////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Failed: id is 0
    @Test
    void getTaskById_IdIsZero_Failure() {
        Assertions.assertThrows(
                FailedToGetTask.class,
                () -> service.getTaskById(0),
                "Failed to get task: id is bad");
    }

    // Failed: id doesn't exist
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
    @Test
    void getTaskByDate_OneTasks_Success() throws FailedToGetTask {
        List<Task> tasks = new ArrayList<>();
        tasks.add(savedTask);

        when(taskRepo.findByDateInitiated(task.getDateInitiated())).thenReturn(tasks);

        List<Task> foundTasks = service.getTasksByDate(task.getDateInitiated().format(DateTimeFormatter.ISO_DATE));

        Assertions.assertNotEquals(0, foundTasks.size());
    }

    // Success: 0 tasks
    @Test
    void getTaskByDate_ZeroTask_Success() throws FailedToGetTask {
        List<Task> tasks = new ArrayList<>();

        when(taskRepo.findByDateInitiated(task.getDateInitiated())).thenReturn(tasks);

        List<Task> foundTasks = service.getTasksByDate(task.getDateInitiated().format(DateTimeFormatter.ISO_DATE));

        Assertions.assertEquals(0, foundTasks.size());
    }

    // Success: does not throw error
    @Test
    void getTaskByDate_DoesNotThrowError_Success() {
        List<Task> tasks = new ArrayList<>();

        when(taskRepo.findByDateInitiated(task.getDateInitiated())).thenReturn(tasks);

        Assertions.assertDoesNotThrow(() -> service.getTasksByDate(task.getDateInitiated().format(DateTimeFormatter.ISO_DATE)));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get Tasks By Date FAILED         //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Failed: Date is invalid
    @Test
    void getTaskByDate_InvalidDate_Failure() {

        Assertions.assertThrows(FailedToGetTask.class,
                () -> service.getTasksByDate("null"),
                "Failed to get task: date is invalid: null");
    }

    // Failed: Date is empty string
    @Test
    void getTaskByDate_EmptyStringDate_Failure() {

        Assertions.assertThrows(FailedToGetTask.class,
                () -> service.getTasksByDate(""),
                "Failed to get task: date is invalid: ");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get Tasks By Type SUCCESS         /////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Success: Get Tasks by Type
    @Test
    void getTasksByType_Correct_Success() throws FailedToGetTask {
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
    @Test
    void getTaskByType_NameNotInTable_Failure() {
        task.setType(new TaskType(2,"TASK TEST FAIL"));

        Assertions.assertThrows(FailedToGetTask.class,
                () -> service.getTasksByType(task.getType().getName()),
                "Failed to get task: Type is invalid: TASK TEST FAIL");
    }

    // Failed: Type is null
    @Test
    void getTaskByType_IsNull_Failure() {
        task.setType(null);

        Assertions.assertThrows(FailedToGetTask.class,
                () -> service.getTasksByType(null),
                "Failed to get task: Type is invalid: null");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get All Tasks SUCCESS         /////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Success: 1+ tasks
    @Test
    void getAllTasks_OneOrMore_Success() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(savedTask);

        when(taskRepo.findAll()).thenReturn(tasks);

        List<Task> allTasks = service.getAllTasks();

        Assertions.assertNotEquals(0, allTasks.size());
    }

    // Success: no tasks
    @Test
    void getAllTasks_NoTasks_Success() {
        List<Task> tasks = new ArrayList<>();

        when(taskRepo.findAll()).thenReturn(tasks);

        List<Task> allTasks = service.getAllTasks();

        Assertions.assertEquals(0, allTasks.size());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Update Task SUCCESS         //////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Success: Correct Status
    @Test
    void updateTaskStatus_Correct_Success() throws FailedToUpdateTask {
        task.setStatus(new TaskStatus(2, "Completed"));
        savedTask.setStatus(new TaskStatus(2, "Completed"));

        when(taskRepo.save(task)).thenReturn(savedTask);
        when(taskRepo.existsById(task.getId())).thenReturn(true);

        Task newTask = service.updateTaskStatus(task);
        Assertions.assertEquals(task.getStatus().getName(), newTask.getStatus().getName());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Update Task FAILED         /////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Failed: Type null
    @Test
    void updateTaskStatus_NullType_Failure() {
        Task failTask = new Task(0,
                "Test Task",
                null,
                new TaskStatus(1, "not started"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToUpdateTask.class,
                () -> service.updateTaskStatus(failTask),
                "Failed to update: task is invalid");
    }

    // Failed: Type doesn't exist
    @Test
    void updateTaskStatus_TypeNotExist_Failure() {
        Task failTask = new Task(0,
                "Test Task",
                new TaskType(3, "TEST"),
                new TaskStatus(1, "not started"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToUpdateTask.class,
                () -> service.updateTaskStatus(failTask),
                "Failed to update: task is invalid");
    }

    // Failed: Name null
    @Test
    void updateTaskStatus_NullName_Failure() {
        Task failTask = new Task(0,
                null,
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToUpdateTask.class,
                () -> service.updateTaskStatus(failTask),
                "Failed to update: task is invalid");
    }

    // Failed: Empty String Name
    @Test
    void updateTaskStatus_EmptyStringName_Failure() {
        Task failTask = new Task(0,
                "",
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToUpdateTask.class,
                () -> service.updateTaskStatus(failTask),
                "Failed to update: task is invalid");
    }

    // Failed: Status null
    @Test
    void updateTaskStatus_NullStatus_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                null,
                new RecurrTask(1, "WEEKLY", "TUESDAY", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToUpdateTask.class,
                () -> service.updateTaskStatus(failTask),
                "Failed to update: task is invalid");
    }

    // Failed: Status doesn't exist
    @Test
    void updateTaskStatus_StatusNotExist_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                new TaskStatus(3, "TEST"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToUpdateTask.class,
                () -> service.updateTaskStatus(failTask),
                "Failed to update: task is invalid");
    }

    // Failed: null recurrence
    @Test
    void updateTaskStatus_NullRecurrence_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                null,
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToUpdateTask.class,
                () -> service.updateTaskStatus(failTask),
                "Failed to update: task is invalid");
    }

    // Failed: Recurrence doesn't exist
    @Test
    void updateTaskStatus_RecurrenceNotExist_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                new RecurrTask(3, "WEEKLY", "TEST", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToUpdateTask.class,
                () -> service.updateTaskStatus(failTask),
                "Failed to update: task is invalid");
    }

    // Failed: null date initiated
    @Test
    void updateTaskStatus_NullInitiateDate_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", LocalDate.parse("2023-12-28")),
                null,
                null,
                false);

        Assertions.assertThrows(
                FailedToUpdateTask.class,
                () -> service.updateTaskStatus(failTask),
                "Failed to update: task is invalid");
    }

    // Failed: task doesn't exist
    @Test
    void updateTaskStatus_taskNotExist_Failure() {
        Task failTask = new Task(0,
                "test task",
                new TaskType(1, "Cleaning"),
                new TaskStatus(1, "not started"),
                new RecurrTask(1, "WEEKLY", "TUESDAY", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);


        when(taskRepo.existsById(task.getId())).thenReturn(false);

        Assertions.assertThrows(
                FailedToUpdateTask.class,
                () -> service.updateTaskStatus(failTask),
                "Failed to update: task is invalid");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Complete Task SUCCESS         /////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Success: complete task
    @Test
    void completeTask_NullCompleteDate_Success() throws FailedToCompleteTask {
        savedTask.setComplete(true);
        savedTask.setDateCompleted(new Date(System.currentTimeMillis()).toLocalDate());
        savedTask.setStatus(new TaskStatus(1,"COMPLETED"));

        when(taskRepo.findById(task.getId())).thenReturn(savedTask);
        when(statusRepo.findByName("COMPLETED")).thenReturn(new TaskStatus(1,"COMPLETED"));
        when(taskRepo.save(savedTask)).thenReturn(savedTask);

        Task newTask = service.complete(task.getId());

        Assertions.assertTrue(newTask.isComplete());
        Assertions.assertNotEquals(task.getDateCompleted(), newTask.getDateCompleted());
        Assertions.assertNotEquals(task.getStatus().getName(), newTask.getStatus().getName());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Complete Task FAILED         //////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Failed: complete task
    @Test
    void completeTask_ThrowError_Failure() {
        Assertions.assertThrows(FailedToCompleteTask.class,
                () -> service.complete(1),
                "Failed to complete task: task doesn't exist");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Delete Task SUCCESS         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Success: delete task
    @Test
    void deleteTask_Correct_Success() throws FailedToDeleteTask {
        when(taskRepo.existsById(task.getId())).thenReturn(false);

        String deleted = service.deleteTask(task.getId());
        Assertions.assertEquals("Successfully Deleted", deleted);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Delete Task FAILED         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Failed: delete task
    @Test
    void deleteTask_DidNotDelete_Failure() {
        when(taskRepo.existsById(task.getId())).thenReturn(true);

        Assertions.assertThrows(FailedToDeleteTask.class,
                () -> service.deleteTask(task.getId()),
                "Failed to delete task");

    }
}
