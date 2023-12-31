package dev.brooskiey.taskmodule.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.brooskiey.taskmodule.TaskModuleApplication;
import dev.brooskiey.taskmodule.exceptions.*;
import dev.brooskiey.taskmodule.models.*;
import dev.brooskiey.taskmodule.services.TaskService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes= TaskModuleApplication.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class TaskEntityControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    MockMvc mockMvc;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    TaskService service;

    TaskEntity failTaskEntity;
    TaskEntity task;
    List<TaskEntity> taskEntities = new ArrayList<>();

    // Set up
    @BeforeAll
    void setup() {
        CategoryEntity category = new CategoryEntity(1, "WEEKLY");
        RecurrenceEntity recurrence = new RecurrenceEntity(1, category, "TUESDAY", LocalDate.parse("2023-12-28"));
        StatusEntity status = new StatusEntity(1, "not started");
        TypeEntity type = new TypeEntity(1, "Cleaning");

        failTaskEntity = new TaskEntity(0,
                "Test TaskEntity",
                type,
                status,
                null,
                LocalDate.parse("2023-12-28"),
                null,
                false);

        task = new TaskEntity(0, "Test TaskEntity", type, status, recurrence,
                LocalDate.parse("2023-12-27"), null, false);

        taskEntities.add(task);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Create TaskEntity SUCCESS         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void createTask_Success() throws Exception {
        when(service.createTask(any())).thenReturn(task);

        this.mockMvc.perform(post("/tasks")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(task)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Create TaskEntity FAILED         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Test
    void createTask_Failure() throws Exception {
        when(service.createTask(any())).thenThrow(FailedToCreateTask.class);

        this.mockMvc.perform(post("/tasks")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(task)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get All Tasks SUCCESS         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void getAllTasks_Success() throws Exception {
        when(service.getAllTasks()).thenReturn(taskEntities);

        this.mockMvc.perform(get("/tasks")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get TaskEntity By Id SUCCESS         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void getTaskById_Success() throws Exception {
        when(service.getTaskById(anyLong())).thenReturn(task);

        this.mockMvc.perform(get("/tasks/1")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get TaskEntity By Id FAILED         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Test
    void getTaskById_Failure() throws Exception {
        when(service.getTaskById(anyLong())).thenThrow(FailedToGetTask.class);

        this.mockMvc.perform(get("/tasks/1")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get Tasks By Date SUCCESS         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void getTasksByDate_Success() throws Exception {
        when(service.getTasksByDate(anyString())).thenReturn(taskEntities);

        this.mockMvc.perform(get("/tasks/days/2023-12-28")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get Tasks By Date FAILED         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Test
    void getTasksByDate_Failure() throws Exception {
        when(service.getTasksByDate(anyString())).thenThrow(FailedToGetTask.class);

        this.mockMvc.perform(get("/tasks/days/2023-12-28")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get Tasks By Week SUCCESS         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void getTasksByWeek_Success() throws Exception {
        when(service.getTasksByWeek("2023-12-25")).thenReturn(taskEntities);

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
        when(service.getTasksByWeek("2023-12-25")).thenThrow(FailedToGetTask.class);

        this.mockMvc.perform(get("/tasks/weeks/2023-12-25").contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get Tasks By TypeEntity SUCCESS         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void getTasksByType_Success() throws Exception {
        when(service.getTasksByType(anyString())).thenReturn(taskEntities);

        this.mockMvc.perform(get("/tasks/types/Cleaning")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Get Tasks By TypeEntity FAILED         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Test
    void getTasksByType_Failure() throws Exception {
        when(service.getTasksByType(anyString())).thenThrow(FailedToGetTask.class);

        this.mockMvc.perform(get("/tasks/types/Cleaning")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Update TaskEntity SUCCESS         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void updateTask_Success() throws Exception {
        when(service.updateTask(any())).thenReturn(task);

        this.mockMvc.perform(put("/tasks")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(task)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Update TaskEntity FAILED         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Test
    void updateTasks_Failure() throws Exception {
        when(service.updateTask(any())).thenThrow(FailedToUpdateTask.class);

        this.mockMvc.perform(put("/tasks")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(task)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Complete TaskEntity SUCCESS         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void completeTask_Success() throws Exception {
        when(service.complete(anyLong())).thenReturn(task);

        this.mockMvc.perform(put("/tasks/complete/1")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Complete TaskEntity FAILED         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Test
    void completeTask_Failure() throws Exception {
        when(service.complete(anyLong())).thenThrow(FailedToCompleteTask.class);

        this.mockMvc.perform(put("/tasks/complete/1")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Delete TaskEntity SUCCESS         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void deleteTaskById_Success() throws Exception {
        when(service.deleteTask(anyLong())).thenReturn("Successfully Deleted");

        this.mockMvc.perform(delete("/tasks/1")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////             Delete TaskEntity FAILED         ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void deleteTaskById_Failure() throws Exception {
        when(service.deleteTask(anyLong())).thenThrow(FailedToDeleteTask.class);

        this.mockMvc.perform(delete("/tasks/1")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"));
    }

}
