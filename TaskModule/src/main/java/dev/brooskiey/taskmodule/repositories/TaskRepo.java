package dev.brooskiey.taskmodule.repositories;

import dev.brooskiey.taskmodule.models.TaskEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Task Repository
 * @author Brooskiey Bullet
 * @version 01.01.2024
 */
@Repository
public interface TaskRepo extends CrudRepository<TaskEntity, Long> {

    /**
     * Find the task by id
     * @param id id of the task
     * @return the task record
     */
    TaskEntity findById(long id);

    /**
     * Get all the tasks for a specific date
     * @param date the date of the tasks
     * @return a list of tasks
     */
    List<TaskEntity> findByDateInitiated(LocalDate date);

    /**
     * Get all the tasks for a specific recurrence
     * @param id id of the recurrence record
     * @return list of tasks
     */
    List<TaskEntity> findByRecurrenceId(long id);

    /**
     * Get the tasks by the name
     * @param name name of the task
     * @return a list of tasks
     */
    List<TaskEntity> findByName(String name);

    /**
     * Get the tasks by the type name
     * @param type name of the type
     * @return a list of tasks
     */
    @Query("SELECT t FROM TaskEntity t WHERE t.type.name like ?1")
    List<TaskEntity> findByType(String type);
}
