package dev.brooskiey.personalplanner.controllers;

import dev.brooskiey.personalplanner.dtos.TaskDTO;
import dev.brooskiey.personalplanner.exceptions.*;
import dev.brooskiey.personalplanner.models.Task;
import dev.brooskiey.personalplanner.services.TaskService;
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
    public Task createTask(@RequestBody TaskDTO taskDTO) throws FailedToCreateTask {
        Task task = convertToDto(taskDTO);
        return service.createTask(task);
}

    @GetMapping(value="",
            consumes="application/json",
            produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getAllTasks() {
        return service.getAllTasks();
    }

    @GetMapping(value="/{id}",
            produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public Task getTaskById(@PathVariable Long id) throws FailedToGetTask {
        return service.getTaskById(id);
    }

    @GetMapping(value="/days/{date}",
            produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getTasksByDate(@PathVariable String date) throws FailedToGetTask {
        return service.getTasksByDate(date);
    }

    @GetMapping(value="/weeks/{date}",
            produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getTasksByWeek(@PathVariable String date) {
        return null;
    }

    @GetMapping(value="/types/{typeName}",
            produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getTasksByType(@PathVariable String typeName) {
        return service.getTasksByType(typeName);
    }

    @PutMapping(value="",
            consumes="application/json",
            produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public Task updateTask(@RequestBody TaskDTO taskDTO) throws FailedToUpdateTask {
        Task task = convertToDto(taskDTO);
        return service.updateTask(task);
    }

    @PutMapping(value="/complete/{id}",
            consumes="application/json",
            produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public Task completeTask(@PathVariable("id") long id) throws FailedToCompleteTask {
        return service.complete(id);
    }

    @DeleteMapping(value="/{id}",
            produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public String deleteById(@PathVariable long id) throws FailedToDeleteTask {
        return service.deleteTask(id);
    }

    private Task convertToDto(TaskDTO taskDTO) {
        return modelMapper.map(taskDTO, Task.class);
    }
}
