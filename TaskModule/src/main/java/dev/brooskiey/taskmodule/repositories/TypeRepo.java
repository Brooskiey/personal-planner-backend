package dev.brooskiey.taskmodule.repositories;

import dev.brooskiey.taskmodule.models.TypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Type Repository
 * @author Brooskiey Bullet
 * @version 01.01.2024
 */
@Repository
public interface TypeRepo extends CrudRepository<TypeEntity, Long> {
}
