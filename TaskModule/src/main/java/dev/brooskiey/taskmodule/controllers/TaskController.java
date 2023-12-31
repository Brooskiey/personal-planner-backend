package dev.brooskiey.taskmodule.controllers;

import dev.brooskiey.taskmodule.dtos.TaskDTO;
import dev.brooskiey.taskmodule.exceptions.*;
import dev.brooskiey.taskmodule.models.TaskEntity;
import dev.brooskiey.taskmodule.services.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/tasks")
public class TaskController {

    TaskService service;
    ModelMapper modelMapper = new ModelMapper();

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping(value="",
            consumes="application/json",
            produces="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskEntity createTask(@RequestBody TaskDTO taskDTO) throws FailedToCreateTask {
        TaskEntity task = convertToDto(taskDTO);
        return service.createTask(task);
}

    @GetMapping(value="",
            consumes="application/json",
            produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskEntity> getAllTasks() {
        return service.getAllTasks();
    }

    @GetMapping(value="/{id}",
            produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public TaskEntity getTaskById(@PathVariable Long id) throws FailedToGetTask {
        return service.getTaskById(id);
    }

    @GetMapping(value="/days/{date}",
            produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskEntity> getTasksByDate(@PathVariable String date) throws FailedToGetTask {
        return service.getTasksByDate(date);
    }

    @GetMapping(value="/weeks/{date}",
            produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskEntity> getTasksByWeek(@PathVariable String date) {
        return service.getTasksByWeek(date);
    }

    @GetMapping(value="/types/{typeName}",
            produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskEntity> getTasksByType(@PathVariable String typeName) {
        return service.getTasksByType(typeName);
    }

    @PutMapping(value="",
            consumes="application/json",
            produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public TaskEntity updateTask(@RequestBody TaskDTO taskDTO) throws FailedToUpdateTask {
        TaskEntity task = convertToDto(taskDTO);
        return service.updateTask(task);
    }

    @PutMapping(value="/complete/{id}",
            consumes="application/json",
            produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public TaskEntity completeTask(@PathVariable("id") long id) throws FailedToCompleteTask {
        return service.complete(id);
    }

    @DeleteMapping(value="/{id}",
            produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public String deleteById(@PathVariable long id) throws FailedToDeleteTask {
        return service.deleteTask(id);
    }

    private TaskEntity convertToDto(TaskDTO taskDTO) {
        return modelMapper.map(taskDTO, TaskEntity.class);
    }
}
