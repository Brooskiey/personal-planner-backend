package dev.brooskiey.personalplanner.repositories;

import dev.brooskiey.personalplanner.models.TaskType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskTypeRepo extends CrudRepository<TaskType, Long> {
}
