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
        newTask.setId(0);
        newTask.setComplete(false);
        newTask.setDateCompleted(null);
        if(isValidTask(newTask)) {
            return taskRepo.save(newTask);
        }
        else {
            throw new FailedToCreateTask("Failed to create: task is invalid");
        }
    }

    // Get task by id
    public Task getTaskById(long id) throws FailedToGetTask {
        if(id == 0) {
            throw new FailedToGetTask("Failed to get task: id is bad");
        }
        Task task = taskRepo.findById(id);
        if(task == null) {
            throw new FailedToGetTask("Failed to get task: task does not exist");
        }
        return task;
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

    // Helper for determining the validity of the task
    private boolean isValidTask(Task task) {
        return task.getType() != null
                && Type.contains(task.getType().getName())
                && task.getName() != null
                && !task.getName().equals("")
                && task.getStatus() != null
                && Status.contains(task.getStatus().getName())
                && task.getRecurrence() != null
                && Recurrence.contains(task.getRecurrence().getRecurrence())
                && Category.contains(task.getRecurrence().getCategory())
                && task.getDateInitiated() != null;
    }
}
