package dev.brooskiey.taskmodule.services;

import dev.brooskiey.taskmodule.TaskModuleApplication;
import dev.brooskiey.taskmodule.exceptions.*;
import dev.brooskiey.taskmodule.models.*;
import dev.brooskiey.taskmodule.repositories.StatusRepo;
import dev.brooskiey.taskmodule.repositories.TaskRepo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes= TaskModuleApplication.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class TaskEntityServiceTest {

    @Mock
    TaskRepo taskRepo;

    @Mock
    StatusRepo statusRepo;

    @InjectMocks
    TaskService service;

    TaskEntity task;
    TaskEntity savedTaskEntity;

    // Set up
    @BeforeAll
    void setup() {
        CategoryEntity category = new CategoryEntity(1, "WEEKLY");
        RecurrenceEntity recurrence = new RecurrenceEntity(1, category, "TUESDAY", LocalDate.parse("2023-12-28"));
        StatusEntity status = new StatusEntity(1, "not started");
        TypeEntity type = new TypeEntity(1, "Cleaning");
        task = new TaskEntity(0, "Test TaskEntity", type, status, recurrence,
                LocalDate.parse("2023-12-27"), null, false);
        savedTaskEntity = new TaskEntity(1, "Test TaskEntity", type, status, recurrence,
                LocalDate.parse("2023-12-28"), null, false);

    }

    @BeforeEach
    void beforeEach() {
        CategoryEntity category = new CategoryEntity(1, "WEEKLY");
        RecurrenceEntity recurrence = new RecurrenceEntity(1, category, "TUESDAY", LocalDate.parse("2023-12-28"));
        
        StatusEntity status = new StatusEntity(1, "not started");
        TypeEntity type = new TypeEntity(1, "Cleaning");
        task = new TaskEntity(0, "Test TaskEntity", type, status, recurrence,
                LocalDate.parse("2023-12-27"), null, false);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Create TaskEntity SUCCESS         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Success: correct
    @Test
    void createTask_Correct_Success() throws FailedToCreateTask {
        when(taskRepo.save(any())).thenReturn(savedTaskEntity);

        TaskEntity newTask = service.createTask(task);
        Assertions.assertNotEquals(0, newTask.getId());
    }

    // Success: null date initiated
    @Test
    void createTask_NullInitiateDate_Failure() {
        when(taskRepo.save(any())).thenReturn(savedTaskEntity);

        task.setDateInitiated(null);

        TaskEntity newTask = service.createTask(task);
        Assertions.assertNotNull(newTask.getDateInitiated());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Create TaskEntity FAILED         ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Failed: null type
    @Test
    void createTask_NullType_Failure() {
        TaskEntity failTaskEntity = new TaskEntity(0,
                "Test TaskEntity",
                null,
                new StatusEntity(1, "not started"),
                new RecurrenceEntity(1, new CategoryEntity(1, "WEEKLY"), "TUESDAY", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.createTask(failTaskEntity),
                "Failed to create: task is invalid");
    }

    // Failed: TypeEntity doesn't exist
    @Test
    void createTask_TypeNotExist_Failure() {
        TaskEntity failTaskEntity = new TaskEntity(0,
                "Test TaskEntity",
                new TypeEntity(3, "TEST"),
                new StatusEntity(1, "not started"),
                new RecurrenceEntity(1, new CategoryEntity(1, "WEEKLY"), "TUESDAY", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.createTask(failTaskEntity),
                "Failed to create: task is invalid");
    }

    // Failed: null name
    @Test
    void createTask_NullName_Failure() {
        TaskEntity failTaskEntity = new TaskEntity(0,
                null,
                new TypeEntity(1, "Cleaning"),
                new StatusEntity(1, "not started"),
                new RecurrenceEntity(1, new CategoryEntity(1, "WEEKLY"), "TUESDAY", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.createTask(failTaskEntity),
                "Failed to create: task is invalid");
    }

    // Failed: empty String name
    @Test
    void createTask_EmptyStringName_Failure() {
        TaskEntity failTaskEntity = new TaskEntity(0,
                "",
                new TypeEntity(1, "Cleaning"),
                new StatusEntity(1, "not started"),
                new RecurrenceEntity(1, new CategoryEntity(1, "WEEKLY"), "TUESDAY", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.createTask(failTaskEntity),
                "Failed to create: task is invalid");
    }

    // Failed: null status
    @Test
    void createTask_NullStatus_Failure() {
        TaskEntity failTaskEntity = new TaskEntity(0,
                "test task",
                new TypeEntity(1, "Cleaning"),
                null,
                new RecurrenceEntity(1, new CategoryEntity(1, "WEEKLY"), "TUESDAY", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.createTask(failTaskEntity),
                "Failed to create: task is invalid");
    }

    // Failed: StatusEntity doesn't exist
    @Test
    void createTask_StatusNotExist_Failure() {
        TaskEntity failTaskEntity = new TaskEntity(0,
                "test task",
                new TypeEntity(1, "Cleaning"),
                new StatusEntity(3, "TEST"),
                new RecurrenceEntity(1, new CategoryEntity(1, "WEEKLY"), "TUESDAY", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.createTask(failTaskEntity),
                "Failed to create: task is invalid");
    }

    // Failed: null recurrence
    @Test
    void createTask_NullRecurrence_Failure() {
        TaskEntity failTaskEntity = new TaskEntity(0,
                "test task",
                new TypeEntity(1, "Cleaning"),
                new StatusEntity(1, "not started"),
                null,
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.createTask(failTaskEntity),
                "Failed to create: task is invalid");
    }

    // Failed: RecurrenceEntity doesn't exist
    @Test
    void createTask_RecurrenceNotExist_Failure() {
        TaskEntity failTaskEntity = new TaskEntity(0,
                "test task",
                new TypeEntity(1, "Cleaning"),
                new StatusEntity(1, "not started"),
                new RecurrenceEntity(3, new CategoryEntity(1, "WEEKLY"), "TEST", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.createTask(failTaskEntity),
                "Failed to create: task is invalid");
    }

    // Failed: Category doesn't exist
    @Test
    void createTask_CategoryNotExist_Failure() {
        TaskEntity failTaskEntity = new TaskEntity(0,
                "test task",
                new TypeEntity(1, "Cleaning"),
                new StatusEntity(1, "not started"),
                new RecurrenceEntity(3, new CategoryEntity(1, "TEST"), "TUESDAY", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToCreateTask.class,
                () -> service.createTask(failTaskEntity),
                "Failed to create: task is invalid");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get TaskEntity By Id SUCCESS         ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Get TaskEntity by Id Success
    @Test
    void getTaskById_Correct_Success() {
        when(taskRepo.findById(anyLong())).thenReturn(savedTaskEntity);

        Assertions.assertDoesNotThrow(() -> service.getTaskById(1));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get TaskEntity By Id FAILED         /////////////////////////////////////////////////////
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
        when(taskRepo.findById(anyLong())).thenReturn(null);

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
        List<TaskEntity> taskEntities = new ArrayList<>();
        taskEntities.add(savedTaskEntity);

        when(taskRepo.findByDateInitiated(any())).thenReturn(taskEntities);

        List<TaskEntity> foundTaskEntities = service.getTasksByDate(task.getDateInitiated().format(DateTimeFormatter.ISO_DATE));

        Assertions.assertNotEquals(0, foundTaskEntities.size());
    }

    // Success: 0 tasks
    @Test
    void getTaskByDate_ZeroTask_Success() throws FailedToGetTask {
        List<TaskEntity> taskEntities = new ArrayList<>();

        when(taskRepo.findByDateInitiated(any())).thenReturn(taskEntities);

        List<TaskEntity> foundTaskEntities = service.getTasksByDate(task.getDateInitiated().format(DateTimeFormatter.ISO_DATE));

        Assertions.assertEquals(0, foundTaskEntities.size());
    }

    // Success: does not throw error
    @Test
    void getTaskByDate_DoesNotThrowError_Success() {
        List<TaskEntity> taskEntities = new ArrayList<>();

        when(taskRepo.findByDateInitiated(any())).thenReturn(taskEntities);

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
    ////////////////////             Get Tasks By Week SUCCESS         /////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Success: Days of the week
    @ParameterizedTest
    @ValueSource(strings = {"2023-12-25","2023-12-26","2023-12-27","2023-12-28","2023-12-29","2023-12-30","2023-12-31"})
    void getTaskByWeek_MondayTasks_Success(String day) throws FailedToGetTask {
        List<TaskEntity> taskEntities = new ArrayList<>();
        taskEntities.add(savedTaskEntity);

        when(taskRepo.findByDateInitiated(any())).thenReturn(taskEntities);

        List<TaskEntity> foundTaskEntities = service.getTasksByWeek(day);

        Assertions.assertEquals(7, foundTaskEntities.size());
    }

    // Success: 0 tasks
    @Test
    void getTaskByWeek_ZeroTask_Success() throws FailedToGetTask {
        List<TaskEntity> taskEntities = new ArrayList<>();

        when(taskRepo.findByDateInitiated(any())).thenReturn(taskEntities);

        List<TaskEntity> foundTaskEntities = service.getTasksByWeek(task.getDateInitiated().format(DateTimeFormatter.ISO_DATE));

        Assertions.assertEquals(0, foundTaskEntities.size());
    }

    // Success: does not throw error
    @Test
    void getTaskByWeek_DoesNotThrowError_Success() {
        List<TaskEntity> taskEntities = new ArrayList<>();

        when(taskRepo.findByDateInitiated(any())).thenReturn(taskEntities);

        Assertions.assertDoesNotThrow(() -> service.getTasksByWeek(task.getDateInitiated().format(DateTimeFormatter.ISO_DATE)));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get Tasks By Week FAILED         //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Failed: Date is invalid
    @Test
    void getTaskByWeek_InvalidDate_Failure() {

        Assertions.assertThrows(FailedToGetTask.class,
                () -> service.getTasksByWeek("null"),
                "Failed to get task: date is invalid: null");
    }

    // Failed: Date is empty string
    @Test
    void getTaskByWeek_EmptyStringDate_Failure() {

        Assertions.assertThrows(FailedToGetTask.class,
                () -> service.getTasksByWeek(""),
                "Failed to get task: date is invalid: ");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get Tasks By TypeEntity SUCCESS         /////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Success: Get Tasks by TypeEntity
    @Test
    void getTasksByType_Correct_Success() throws FailedToGetTask {
        List<TaskEntity> taskEntities = new ArrayList<>();
        taskEntities.add(savedTaskEntity);

        when(taskRepo.findByType(anyString())).thenReturn(taskEntities);

        List<TaskEntity> foundTaskEntities = service.getTasksByType(task.getType().getName());

        Assertions.assertNotEquals(0, foundTaskEntities.size());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get Tasks By TypeEntity FAILED         //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Failed: TypeEntity name doesn't exist
    @Test
    void getTaskByType_NameNotInTable_Failure() {
        task.setType(new TypeEntity(2,"TASK TEST FAIL"));

        Assertions.assertThrows(FailedToGetTask.class,
                () -> service.getTasksByType("TASK TEST FAIL"),
                "Failed to get task: TypeEntity is invalid: TASK TEST FAIL");
    }

    // Failed: TypeEntity is null
    @Test
    void getTaskByType_IsNull_Failure() {
        task.setType(null);

        Assertions.assertThrows(FailedToGetTask.class,
                () -> service.getTasksByType(null),
                "Failed to get task: TypeEntity is invalid: null");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get All Tasks SUCCESS         /////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Success: 1+ tasks
    @Test
    void getAllTasks_OneOrMore_Success() {
        List<TaskEntity> taskEntities = new ArrayList<>();
        taskEntities.add(savedTaskEntity);

        when(taskRepo.findAll()).thenReturn(taskEntities);

        List<TaskEntity> allTaskEntities = service.getAllTasks();

        Assertions.assertNotEquals(0, allTaskEntities.size());
    }

    // Success: no tasks
    @Test
    void getAllTasks_NoTasks_Success() {
        List<TaskEntity> taskEntities = new ArrayList<>();

        when(taskRepo.findAll()).thenReturn(taskEntities);

        List<TaskEntity> allTaskEntities = service.getAllTasks();

        Assertions.assertEquals(0, allTaskEntities.size());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Update TaskEntity SUCCESS         //////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Success: Correct StatusEntity
    @Test
    void updateTask_Correct_Success() throws FailedToUpdateTask {
        task.setStatus(new StatusEntity(2, "Completed"));
        savedTaskEntity.setStatus(new StatusEntity(2, "Completed"));

        when(taskRepo.save(any())).thenReturn(savedTaskEntity);
        when(taskRepo.existsById(anyLong())).thenReturn(true);

        TaskEntity newTask = service.updateTask(task);
        Assertions.assertEquals(task.getStatus().getName(), newTask.getStatus().getName());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Update TaskEntity FAILED         /////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Failed: TypeEntity null
    @Test
    void updateTask_NullType_Failure() {
        TaskEntity failTaskEntity = new TaskEntity(0,
                "Test TaskEntity",
                null,
                new StatusEntity(1, "not started"),
                new RecurrenceEntity(1, new CategoryEntity(1, "WEEKLY"), "TUESDAY", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToUpdateTask.class,
                () -> service.updateTask(failTaskEntity),
                "Failed to update: task is invalid");
    }

    // Failed: TypeEntity doesn't exist
    @Test
    void updateTask_TypeNotExist_Failure() {
        TaskEntity failTaskEntity = new TaskEntity(0,
                "Test TaskEntity",
                new TypeEntity(3, "TEST"),
                new StatusEntity(1, "not started"),
                new RecurrenceEntity(1, new CategoryEntity(1, "WEEKLY"), "TUESDAY", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToUpdateTask.class,
                () -> service.updateTask(failTaskEntity),
                "Failed to update: task is invalid");
    }

    // Failed: Name null
    @Test
    void updateTask_NullName_Failure() {
        TaskEntity failTaskEntity = new TaskEntity(0,
                null,
                new TypeEntity(1, "Cleaning"),
                new StatusEntity(1, "not started"),
                new RecurrenceEntity(1, new CategoryEntity(1, "WEEKLY"), "TUESDAY", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToUpdateTask.class,
                () -> service.updateTask(failTaskEntity),
                "Failed to update: task is invalid");
    }

    // Failed: Empty String Name
    @Test
    void updateTask_EmptyStringName_Failure() {
        TaskEntity failTaskEntity = new TaskEntity(0,
                "",
                new TypeEntity(1, "Cleaning"),
                new StatusEntity(1, "not started"),
                new RecurrenceEntity(1, new CategoryEntity(1, "WEEKLY"), "TUESDAY", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToUpdateTask.class,
                () -> service.updateTask(failTaskEntity),
                "Failed to update: task is invalid");
    }

    // Failed: StatusEntity null
    @Test
    void updateTask_NullStatus_Failure() {
        TaskEntity failTaskEntity = new TaskEntity(0,
                "test task",
                new TypeEntity(1, "Cleaning"),
                null,
                new RecurrenceEntity(1, new CategoryEntity(1, "WEEKLY"), "TUESDAY", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToUpdateTask.class,
                () -> service.updateTask(failTaskEntity),
                "Failed to update: task is invalid");
    }

    // Failed: StatusEntity doesn't exist
    @Test
    void updateTask_StatusNotExist_Failure() {
        TaskEntity failTaskEntity = new TaskEntity(0,
                "test task",
                new TypeEntity(1, "Cleaning"),
                new StatusEntity(3, "TEST"),
                new RecurrenceEntity(1, new CategoryEntity(1, "WEEKLY"), "TUESDAY", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToUpdateTask.class,
                () -> service.updateTask(failTaskEntity),
                "Failed to update: task is invalid");
    }

    // Failed: null recurrence
    @Test
    void updateTask_NullRecurrence_Failure() {
        TaskEntity failTaskEntity = new TaskEntity(0,
                "test task",
                new TypeEntity(1, "Cleaning"),
                new StatusEntity(1, "not started"),
                null,
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToUpdateTask.class,
                () -> service.updateTask(failTaskEntity),
                "Failed to update: task is invalid");
    }

    // Failed: RecurrenceEntity doesn't exist
    @Test
    void updateTask_RecurrenceNotExist_Failure() {
        TaskEntity failTaskEntity = new TaskEntity(0,
                "test task",
                new TypeEntity(1, "Cleaning"),
                new StatusEntity(1, "not started"),
                new RecurrenceEntity(3, new CategoryEntity(1, "WEEKLY"), "TEST", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);

        Assertions.assertThrows(
                FailedToUpdateTask.class,
                () -> service.updateTask(failTaskEntity),
                "Failed to update: task is invalid");
    }

    // Failed: null date initiated
    @Test
    void updateTask_NullInitiateDate_Failure() {
        TaskEntity failTaskEntity = new TaskEntity(0,
                "test task",
                new TypeEntity(1, "Cleaning"),
                new StatusEntity(1, "not started"),
                new RecurrenceEntity(1, new CategoryEntity(1, "WEEKLY"), "TUESDAY", LocalDate.parse("2023-12-28")),
                null,
                null,
                false);

        Assertions.assertThrows(
                FailedToUpdateTask.class,
                () -> service.updateTask(failTaskEntity),
                "Failed to update: task is invalid");
    }

    // Failed: task doesn't exist
    @Test
    void updateTask_taskNotExist_Failure() {
        TaskEntity failTaskEntity = new TaskEntity(0,
                "test task",
                new TypeEntity(1, "Cleaning"),
                new StatusEntity(1, "not started"),
                new RecurrenceEntity(1, new CategoryEntity(1, "WEEKLY"), "TUESDAY", LocalDate.parse("2023-12-28")),
                LocalDate.parse("2023-12-28"),
                null,
                false);


        when(taskRepo.existsById(anyLong())).thenReturn(false);

        Assertions.assertThrows(
                FailedToUpdateTask.class,
                () -> service.updateTask(failTaskEntity),
                "Failed to update: task is invalid");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Complete TaskEntity SUCCESS         /////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Success: complete task
    @Test
    void completeTask_NullCompleteDate_Success() throws FailedToCompleteTask {
        savedTaskEntity.setComplete(true);
        savedTaskEntity.setDateCompleted(new Date(System.currentTimeMillis()).toLocalDate());
        savedTaskEntity.setStatus(new StatusEntity(1,"COMPLETED"));

        when(taskRepo.findById(anyLong())).thenReturn(savedTaskEntity);
        when(statusRepo.findByName(anyString())).thenReturn(new StatusEntity(1,"COMPLETED"));
        when(taskRepo.save(any())).thenReturn(savedTaskEntity);

        TaskEntity newTask = service.complete(task.getId());

        Assertions.assertTrue(newTask.isComplete());
        Assertions.assertNotEquals(task.getDateCompleted(), newTask.getDateCompleted());
        Assertions.assertNotEquals(task.getStatus().getName(), newTask.getStatus().getName());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Complete TaskEntity FAILED         //////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Failed: complete task
    @Test
    void completeTask_ThrowError_Failure() {
        Assertions.assertThrows(FailedToCompleteTask.class,
                () -> service.complete(1),
                "Failed to complete task: task doesn't exist");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Delete TaskEntity SUCCESS         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Success: delete task
    @Test
    void deleteTask_Correct_Success() throws FailedToDeleteTask {
        when(taskRepo.existsById(anyLong())).thenReturn(false);

        String deleted = service.deleteTask(task.getId());
        Assertions.assertEquals("Successfully Deleted", deleted);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Delete TaskEntity FAILED         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Failed: delete task
    @Test
    void deleteTask_DidNotDelete_Failure() {
        when(taskRepo.existsById(anyLong())).thenReturn(true);

        Assertions.assertThrows(FailedToDeleteTask.class,
                () -> service.deleteTask(0),
                "Failed to delete task");

    }
}
