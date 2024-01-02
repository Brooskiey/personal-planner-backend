package dev.brooskiey.taskmodule.repositories;

import dev.brooskiey.taskmodule.models.RecurrenceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Recurrence Repository
 * @author Brooskiey Bullet
 * @version 01.01.2024
 */
@Repository
public interface RecurrRepo extends CrudRepository<RecurrenceEntity, Long> {

    /**
     * Get the recurrence by id
     * @param id id of the recurrence
     * @return the recurrence record
     */
    RecurrenceEntity findById(long id);

    /**
     * Get the recurrence by the name
     * @param recurrence name of the recurrence
     * @return the recurrence record
     */
    RecurrenceEntity findByRecurrence(String recurrence);
}
