package dev.brooskiey.personalplanner.services;

import dev.brooskiey.personalplanner.exceptions.*;
import dev.brooskiey.personalplanner.models.Task;
import dev.brooskiey.personalplanner.models.TaskStatus;
import dev.brooskiey.personalplanner.repositories.TaskRepo;
import dev.brooskiey.personalplanner.repositories.TaskStatusRepo;
import dev.brooskiey.personalplanner.services.enums.Category;
import dev.brooskiey.personalplanner.services.enums.Recurrence;
import dev.brooskiey.personalplanner.services.enums.Status;
import dev.brooskiey.personalplanner.services.enums.Type;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class TaskService {

    TaskRepo taskRepo;

    TaskStatusRepo statusRepo;

    public TaskService(TaskRepo taskRepo, TaskStatusRepo statusRepo) {
        this.taskRepo = taskRepo;
        this.statusRepo = statusRepo;
    }

    // Create task
    public Task createTask(Task newTask) throws FailedToCreateTask {
        newTask.setId(0);
        newTask.setComplete(false);
        newTask.setDateCompleted(null);
        newTask.setDateInitiated(new Date(System.currentTimeMillis()).toLocalDate());
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
    public List<Task> getTasksByDate(String dateStr) throws FailedToGetTask {
        if(dateStr.equals("")) {
            throw new FailedToGetTask("Failed to get task: date is invalid: " + dateStr);
        }
        try {
            LocalDate date = LocalDate.parse(dateStr);
            return taskRepo.findByDateInitiated(date);
        } catch (DateTimeParseException e) {
            throw new FailedToGetTask("Failed to get task: " + e.getMessage());
        }
    }

    // Get tasks by type
    public List<Task> getTasksByType(String type) throws FailedToGetTask {
        if(!Type.contains(type)) {
            throw new FailedToGetTask("Failed to get task: type is invalid: " + type);
        }
        return taskRepo.findByType(type);
    }

    // Get all tasks
    public List<Task> getAllTasks(){
        return (List<Task>) taskRepo.findAll();
    }

    // Update status
    public Task updateTask(Task task) throws FailedToUpdateTask {
        isUpdateValid(task);
        return taskRepo.save(task);
    }

    // Complete task
    public Task complete(long id) throws FailedToCompleteTask {
        Task task = taskRepo.findById(id);
        if(task == null) {
            throw new FailedToCompleteTask("Failed to complete task: task doesn't exist");
        }
        task.setComplete(true);
        task.setDateCompleted(new Date(System.currentTimeMillis()).toLocalDate());
        TaskStatus status = statusRepo.findByName(Status.valueOf("COMPLETED").getValue());
        task.setStatus(status);
        return taskRepo.save(task);
    }

    // Delete task
    public String deleteTask(long id) throws FailedToDeleteTask {
        taskRepo.deleteById(id);
        if(taskRepo.existsById(id))
            throw new FailedToDeleteTask("Failed to delete task");
        return "Successfully Deleted";
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

    // Helper for determining a valid update
    private void isUpdateValid(Task task) throws FailedToUpdateTask {
        if(!isValidTask(task) || !taskRepo.existsById(task.getId())) {
            throw new FailedToUpdateTask("Failed to update: task is invalid");
        }
    }
}
