package dev.brooskiey.personalplanner.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.brooskiey.personalplanner.PersonalPlannerBackendApplication;
import dev.brooskiey.personalplanner.exceptions.FailedToCreateTask;
import dev.brooskiey.personalplanner.exceptions.FailedToGetTask;
import dev.brooskiey.personalplanner.exceptions.FailedToUpdateTask;
import dev.brooskiey.personalplanner.models.RecurrTask;
import dev.brooskiey.personalplanner.models.Task;
import dev.brooskiey.personalplanner.models.TaskStatus;
import dev.brooskiey.personalplanner.models.TaskType;
import dev.brooskiey.personalplanner.services.TaskService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes= PersonalPlannerBackendApplication.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class TaskControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    MockMvc mockMvc;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    ObjectMapper objectMapper;

    @Mock
    TaskService service;

    Task failTask;
    Task task;
    List<Task> tasks = new ArrayList<>();

    // Set up
    @BeforeAll
    void setup() {
        RecurrTask recurrTask = new RecurrTask(1, "WEEKLY", "TUESDAY", LocalDate.parse("2023-12-28"));
        TaskStatus status = new TaskStatus(1, "not started");
        TaskType type = new TaskType(1, "Cleaning");

        failTask = new Task(0,
                "Test Task",
                type,
                status,
                null,
                LocalDate.parse("2023-12-28"),
                null,
                false);

        task = new Task(0, "Test Task", type, status, recurrTask,
                LocalDate.parse("2023-12-27"), null, false);

        tasks.add(task);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Create Task SUCCESS         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void createTask_Success() throws Exception {
        given(service.createTask(task)).willReturn(task);

        this.mockMvc.perform(post("/tasks").contentType("application/json").content(objectMapper.writeValueAsString(task)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Create Task FAILED         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Test
    void createTask_Failure() throws Exception {
        when(service.createTask(task)).thenThrow(FailedToCreateTask.class);

        this.mockMvc.perform(get("/tasks").contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get All Tasks SUCCESS         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void getAllTasks_Success() throws Exception {
        given(service.getAllTasks()).willReturn(tasks);

        this.mockMvc.perform(get("/tasks").contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get All Tasks FAILED         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Test
    void getAllTasks_Failure() throws Exception {
        when(service.getAllTasks()).thenThrow(FailedToGetTask.class);

        this.mockMvc.perform(get("/tasks").contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get Task By Id SUCCESS         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void getTaskById_Success() throws Exception {
        given(service.getTaskById(1)).willReturn(task);

        this.mockMvc.perform(get("/tasks/1").contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get Task By Id FAILED         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Test
    void getTaskById_Failure() throws Exception {
        when(service.getTaskById(1)).thenThrow(FailedToGetTask.class);

        this.mockMvc.perform(get("/tasks/1").contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get Tasks By Date SUCCESS         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void getTasksByDate_Success() throws Exception {
        given(service.getTasksByDate("2023-12-28")).willReturn(tasks);

        this.mockMvc.perform(get("/tasks/days/2023-12-28").contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get Tasks By Date FAILED         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Test
    void getTasksByDate_Failure() throws Exception {
        when(service.getAllTasks()).thenThrow(FailedToGetTask.class);

        this.mockMvc.perform(get("/tasks/days/2023-12-28").contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get Tasks By Week SUCCESS         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void getTasksByWeek_Success() throws Exception {
        given(service.getTasksByDate("2023-12-25")).willReturn(tasks);

        this.mockMvc.perform(get("/tasks/weeks/2023-12-25").contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get Tasks By Week FAILED         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Test
    void getTasksByWeek_Failure() throws Exception {
        when(service.getTasksByDate("2023-12-25")).thenThrow(FailedToGetTask.class);

        this.mockMvc.perform(get("/tasks/weeks/2023-12-25").contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get Tasks By Type SUCCESS         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void getTasksByType_Success() throws Exception {
        given(service.getTasksByType("Cleaning")).willReturn(tasks);

        this.mockMvc.perform(get("/tasks/types/Cleaning").contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get Tasks By Type FAILED         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Test
    void getTasksByType_Failure() throws Exception {
        when(service.getTasksByType("Cleaning")).thenThrow(FailedToGetTask.class);

        this.mockMvc.perform(get("/tasks/types/Cleaning").contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get All Task SUCCESS         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void updateTask_Success() throws Exception {
        given(service.updateTaskStatus(task)).willReturn(task);

        this.mockMvc.perform(put("/tasks").contentType("application/json").content(objectMapper.writeValueAsString(task)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get All Task FAILED         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Test
    void updateTasks_Failure() throws Exception {
        when(service.updateTaskStatus(task)).thenThrow(FailedToUpdateTask.class);

        this.mockMvc.perform(put("/tasks").contentType("application/json").content(objectMapper.writeValueAsString(task)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Complete Task SUCCESS         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void completeTask_Success() throws Exception {
        given(service.complete(1)).willReturn(task);

        this.mockMvc.perform(put("/tasks/1").contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Complete Task FAILED         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Test
    void completeTask_Failure() throws Exception {
        when(service.complete(1)).thenThrow(FailedToGetTask.class);

        this.mockMvc.perform(put("/tasks/1").contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Delete Task SUCCESS         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void deleteTaskById_Success() throws Exception {
        given(service.deleteTask(1)).willReturn("Successfully Deleted");

        this.mockMvc.perform(get("/tasks/1").contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Delete Task FAILED         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Test
    void deleteTaskById_Failure() throws Exception {
        when(service.deleteTask(1)).thenThrow(FailedToGetTask.class);

        this.mockMvc.perform(get("/tasks/1").contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"));
    }

}