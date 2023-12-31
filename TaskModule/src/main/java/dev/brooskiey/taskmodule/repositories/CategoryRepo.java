package dev.brooskiey.taskmodule.repositories;

import dev.brooskiey.taskmodule.models.CategoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends CrudRepository<CategoryEntity, Long> {
}
