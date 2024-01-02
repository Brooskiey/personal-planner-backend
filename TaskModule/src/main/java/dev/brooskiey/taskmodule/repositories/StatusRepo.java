package dev.brooskiey.taskmodule.repositories;

import dev.brooskiey.taskmodule.models.StatusEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Status Repository
 * @author Brooskiey Bullet
 * @version 01.01.2024
 */
@Repository
public interface StatusRepo extends CrudRepository<StatusEntity, Long> {

    /**
     * Get status by id
     * @param id id of the status
     * @return the status record
     */
    StatusEntity findById(long id);

    /**
     * Get the status by the name
     * @param name name of the status
     * @return the status record
     */
    StatusEntity findByName(String name);
}
