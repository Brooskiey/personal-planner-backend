package dev.brooskiey.taskmodule.services;

import dev.brooskiey.taskmodule.exceptions.*;
import dev.brooskiey.taskmodule.models.StatusEntity;
import dev.brooskiey.taskmodule.models.TaskEntity;
import dev.brooskiey.taskmodule.repositories.StatusRepo;
import dev.brooskiey.taskmodule.repositories.TaskRepo;
import dev.brooskiey.taskmodule.services.enums.Category;
import dev.brooskiey.taskmodule.services.enums.Recurrence;
import dev.brooskiey.taskmodule.services.enums.Type;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for all tasks related modifications
 */
@Service
public class TaskService {

    /** Task repository */
    TaskRepo taskRepo;

    /** Status repository */
    StatusRepo statusRepo;

    /** Constructor injection for the repositories */
    public TaskService(TaskRepo taskRepo, StatusRepo statusRepo) {
        this.taskRepo = taskRepo;
        this.statusRepo = statusRepo;
    }

    /**
     * Create a new task
     * @param newTask information of the new task
     * @return the saved task
     * @throws FailedToCreateTask task is invalid
     */
    public TaskEntity createTask(TaskEntity newTask) throws FailedToCreateTask {
        newTask.setId(0);
        newTask.setComplete(false);
        newTask.setDateCompleted(null);
        newTask.setDateInitiated(new Date(System.currentTimeMillis()).toLocalDate());

        // Save only if it is valid
        if(isValidTask(newTask)) {
            return taskRepo.save(newTask);
        }
        else {
            throw new FailedToCreateTask("Failed to create: task is invalid");
        }
    }

    /**
     * Get a task by it's id
     * @param id id of the task
     * @return the task
     * @throws FailedToGetTask Task was not found or id is invalid
     */
    public TaskEntity getTaskById(long id) throws FailedToGetTask {
        // Throw error if id is 0
        if(id == 0) {
            throw new FailedToGetTask("Failed to get task: id is bad");
        }

        TaskEntity task = taskRepo.findById(id);

        // Throw error if no task exists for that id
        if(task == null) {
            throw new FailedToGetTask("Failed to get task: task does not exist");
        }
        return task;
    }

    /**
     * Get tasks by the date initiated
     * @param dateStr date as a string in the format of 'yyyy-mm-dd'
     * @return a list of tasks
     * @throws FailedToGetTask date is invalid
     */
    public List<TaskEntity> getTasksByDate(String dateStr) throws FailedToGetTask {
        // dateStr can't be empty
        if(dateStr.equals("")) {
            throw new FailedToGetTask("Failed to get task: date is invalid: " + dateStr);
        }

        // Try to change date string to a LocalDate
        try {
            LocalDate date = LocalDate.parse(dateStr);
            return taskRepo.findByDateInitiated(date);

        // Catch the failure and throw the custom exception
        } catch (DateTimeParseException e) {
            throw new FailedToGetTask("Failed to get task: " + e.getMessage());
        }
    }

    /**
     * Get the tasks for the week. Week is defined at M - U
     * @param dateStr date as a string in the format of 'yyyy-mm-dd'
     * @return a list of tasks
     * @throws FailedToGetTask date is invalid
     */
    public List<TaskEntity> getTasksByWeek(String dateStr) throws FailedToGetTask {
        // dateStr can't be empty
        if(dateStr.equals("")) {
            throw new FailedToGetTask("Failed to get task: date is invalid: " + dateStr);
        }

        // Try to make date string to a LocalDate
        try {
            int i = 0;

            LocalDate date = LocalDate.parse(dateStr);
            List<TaskEntity> taskEntities = new ArrayList<>();

            // Go back in days until Monday of that week is found
            int day = moveDateToMonday(date);
            date = date.minusDays(day);

            // Iterate through the 7 day week and get the tasks for those dates
            while (i < 7) {
                List<TaskEntity> tasksForDate = getTasksByDate(date.format(DateTimeFormatter.ISO_DATE));
                taskEntities.addAll(tasksForDate);
                date = date.plusDays(1);
                i++;
            }
            return taskEntities;

        // Catch the failure and throw the custom exception
        } catch (DateTimeParseException e) {
            throw new FailedToGetTask("Failed to get task: " + e.getMessage());
        }
    }

    /**
     * Get the tasks by the types
     * @param type type name
     * @return a list of tasks
     * @throws FailedToGetTask type is invalid
     */
    public List<TaskEntity> getTasksByType(String type) throws FailedToGetTask {
        // type must be a Type Enum
        if(!Type.contains(type)) {
            throw new FailedToGetTask("Failed to get task: type is invalid: " + type);
        }
        return taskRepo.findByType(type);
    }

    /**
     * Get all the tasks
     * @return a list of all the tasks
     */
    public List<TaskEntity> getAllTasks(){
        return (List<TaskEntity>) taskRepo.findAll();
    }

    /**
     * Update the task for anything that is not completion
     * @param task new task information
     * @return the updated task
     * @throws FailedToUpdateTask task was invalid
     */
    public TaskEntity updateTask(TaskEntity task) throws FailedToUpdateTask {
        // make sure everything is valid
        isUpdateValid(task);
        return taskRepo.save(task);
    }

    /**
     * Complete the task by adding the completion date and is completed to true
     * @param id id of the task
     * @return the completed task
     * @throws FailedToCompleteTask task is invalid
     */
    public TaskEntity complete(long id) throws FailedToCompleteTask {
        TaskEntity task = taskRepo.findById(id);
        if(task == null) {
            throw new FailedToCompleteTask("Failed to complete task: task doesn't exist");
        }
        task.setComplete(true);
        task.setDateCompleted(new Date(System.currentTimeMillis()).toLocalDate());
        StatusEntity status = statusRepo.findByName(dev.brooskiey.taskmodule.services.enums.Status.valueOf("COMPLETED").getValue());
        task.setStatus(status);
        return taskRepo.save(task);
    }

    /**
     * Delete a task by the id
     * @param id id of the task
     * @return string "Successfully Deleted"
     * @throws FailedToDeleteTask could not delete the task
     */
    public String deleteTask(long id) throws FailedToDeleteTask {
        // Delete the task
        taskRepo.deleteById(id);

        // Make sure it was deleted
        if(taskRepo.existsById(id))
            throw new FailedToDeleteTask("Failed to delete task");

        return "Successfully Deleted";
    }

    /**
     * Helper method to determine if the task sent in the request is valid
     * @param task task information
     * @return true if valid, otherwise false
     */
    private boolean isValidTask(TaskEntity task) {
        return task.getType() != null
                && Type.contains(task.getType().getName())
                && task.getName() != null
                && !task.getName().equals("")
                && task.getStatus() != null
                && dev.brooskiey.taskmodule.services.enums.Status.contains(task.getStatus().getName())
                && task.getRecurrence() != null
                && Recurrence.contains(task.getRecurrence().getRecurrence())
                && Category.contains(task.getRecurrence().getCategory().getName())
                && task.getDateInitiated() != null;
    }

    /**
     * Helper to validate a task information is correct and can be updated
     * @param task task information
     * @throws FailedToUpdateTask task is invalid
     */
    private void isUpdateValid(TaskEntity task) throws FailedToUpdateTask {
        // Make sure the info is valid and the task already exists
        if(!isValidTask(task) || !taskRepo.existsById(task.getId())) {
            throw new FailedToUpdateTask("Failed to update: task is invalid");
        }
    }

    /** Helper for search for tasks in a given week. Takes the day and backs the date sent back to Monday of that week */
    private int moveDateToMonday(LocalDate date) {
        return switch (date.getDayOfWeek()) {
            case MONDAY -> 0;
            case TUESDAY -> 1;
            case WEDNESDAY -> 2;
            case THURSDAY -> 3;
            case FRIDAY -> 4;
            case SATURDAY -> 5;
            case SUNDAY -> 6;
        };
    }
}
