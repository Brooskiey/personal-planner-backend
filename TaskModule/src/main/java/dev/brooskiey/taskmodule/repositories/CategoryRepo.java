package dev.brooskiey.taskmodule.repositories;

import dev.brooskiey.taskmodule.models.CategoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Category Repository
 * @author Brooskiey Bullet
 * @version 01.01.2024
 */
@Repository
public interface CategoryRepo extends CrudRepository<CategoryEntity, Long> {
}
