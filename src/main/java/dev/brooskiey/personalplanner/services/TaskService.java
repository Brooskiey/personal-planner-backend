package dev.brooskiey.personalplanner.services;

import dev.brooskiey.personalplanner.exceptions.FailedToCreateTask;
import dev.brooskiey.personalplanner.exceptions.FailedToGetTask;
import dev.brooskiey.personalplanner.models.Task;
import dev.brooskiey.personalplanner.repositories.TaskRepo;
import dev.brooskiey.personalplanner.services.enums.Category;
import dev.brooskiey.personalplanner.services.enums.Recurrence;
import dev.brooskiey.personalplanner.services.enums.Status;
import dev.brooskiey.personalplanner.services.enums.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    TaskRepo taskRepo;

    // Create task
    public Task createTask(Task newTask) throws FailedToCreateTask {
        return null;
    }

    // Get task by id
    public Task getTaskById(long id) throws FailedToGetTask {
        return null;
    }

    // Get tasks by date
    public List<Task> getTasksByDate(Date date) {
        return null;
    }

    // Get tasks by type
    public List<Task> getTasksByType(String type) {
        return null;
    }

    // Get all tasks
    public List<Task> getAllTasks(){
        return null;
    }

    // Update status
    public Task updateTaskStatus(Task task) {
        return null;
    }

    // Update recurrence
    public Task updateTaskRecurrence(Task task) {
        return null;
    }

    // Update type
    public Task updateTaskType(Task task) {
        return null;
    }

    // Complete task
    public Task complete(long id){
        return null;
    }

    // Delete task
    public String deleteTask(long id) {
        return null;
    }
}
