package dev.brooskiey.personalplanner.controllers;

import dev.brooskiey.personalplanner.dtos.TaskDTO;
import dev.brooskiey.personalplanner.models.Task;
import dev.brooskiey.personalplanner.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping(name="",
            consumes="application/json",
            produces="application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody TaskDTO task){
      return null;
    }

    @GetMapping(name="",
            consumes="application/json",
            produces="application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getAllTasks() {
        return null;
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Task getTasksById(@PathVariable("id") int id) {
        return null;
    }

    @GetMapping("/days/{date}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getTasksByDate(@PathVariable("date") String date) {
        return null;
    }

    @GetMapping("/weeks/{date}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getTasksByWeek(@PathVariable("date") String date) {
        return null;
    }

    @GetMapping("/types/{typeName}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getTasksByType(@PathVariable("typeName") String typeName) {
        return null;
    }

    @PutMapping("")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Task updateTask(@RequestBody TaskDTO task) {
        return null;
    }

    @PutMapping("/complete/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Task completeTask(@PathVariable("id") int id) {
        return null;
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String deleteById(@PathVariable("id") int id) {
        return null;
    }
}
