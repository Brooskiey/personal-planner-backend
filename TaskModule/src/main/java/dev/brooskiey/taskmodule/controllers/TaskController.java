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

/**
 * Controller for all task related modifications
 * @author Brooskiey Bullet
 * @version 01.01.2024
 */

@RestController
@EnableAutoConfiguration
@RequestMapping("/tasks")
public class TaskController {

    TaskService service;
    ModelMapper modelMapper = new ModelMapper();

    /**
     * Constructor with a constructor injected service
     * @param service Service injection
     */
    public TaskController(TaskService service) {
        this.service = service;
    }

    /**
     * Create a task
     * @param taskDTO data transfer object
     * @return the created task with the updated id
     * @throws FailedToCreateTask DTO is invalid or create failed to happen
     */
    @PostMapping(value="",
            consumes="application/json",
            produces="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskEntity createTask(@RequestBody TaskDTO taskDTO) throws FailedToCreateTask {
        TaskEntity task = convertToDto(taskDTO);
        return service.createTask(task);
    }

    /**
     * Get all the tasks
     * @return a list with all the tasks
     */
    @GetMapping(value="",
            consumes="application/json",
            produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskEntity> getAllTasks() {
        return service.getAllTasks();
    }

    /**
     * Get a task by the unique id
     * @param id unique id of the task
     * @return the task with that id
     * @throws FailedToGetTask id doesn't exist
     */
    @GetMapping(value="/{id}",
            produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public TaskEntity getTaskById(@PathVariable Long id) throws FailedToGetTask {
        return service.getTaskById(id);
    }

    /**
     * Get the tasks by date
     * @param date a string in the "YYYY-MM-DD" format
     * @return list of tasks for that date
     * @throws FailedToGetTask date is invalid
     */
    @GetMapping(value="/days/{date}",
            produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskEntity> getTasksByDate(@PathVariable String date) throws FailedToGetTask {
        return service.getTasksByDate(date);
    }

    /**
     * Get all the tasks for a week. Week is defined at M-U
     * @param date a string in the "YYYY-MM-DD" format
     * @return list of tasks for that week
     */
    @GetMapping(value="/weeks/{date}",
            produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskEntity> getTasksByWeek(@PathVariable String date) {
        return service.getTasksByWeek(date);
    }

    /**
     * Get the tasks by their type
     * @param typeName the type of the tasks
     * @return list of tasks of that type
     */
    @GetMapping(value="/types/{typeName}",
            produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskEntity> getTasksByType(@PathVariable String typeName) {
        return service.getTasksByType(typeName);
    }

    /**
     * Update the task for status changes and other changes except for completion
     * @param taskDTO data transfer object
     * @return the updated task
     * @throws FailedToUpdateTask taskDTO is invalid
     */
    @PutMapping(value="",
            consumes="application/json",
            produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public TaskEntity updateTask(@RequestBody TaskDTO taskDTO) throws FailedToUpdateTask {
        TaskEntity task = convertToDto(taskDTO);
        return service.updateTask(task);
    }

    /**
     * Complete the task
     * @param id id of task to complete
     * @return the completed task
     * @throws FailedToCompleteTask unable to complete the task
     */
    @PutMapping(value="/complete/{id}",
            consumes="application/json",
            produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public TaskEntity completeTask(@PathVariable("id") long id) throws FailedToCompleteTask {
        return service.complete(id);
    }

    /**
     * Delete a task
     * @param id id of task to delete
     * @return string: "Successfully Deleted"
     * @throws FailedToDeleteTask Task could not be deleted
     */
    @DeleteMapping(value="/{id}",
            produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public String deleteById(@PathVariable long id) throws FailedToDeleteTask {
        return service.deleteTask(id);
    }

    /**
     * Convert a dto to entity
     * @param taskDTO dto sent in request body
     * @return TaskEntity version of the dto
     */
    private TaskEntity convertToDto(TaskDTO taskDTO) {
        return modelMapper.map(taskDTO, TaskEntity.class);
    }
}
