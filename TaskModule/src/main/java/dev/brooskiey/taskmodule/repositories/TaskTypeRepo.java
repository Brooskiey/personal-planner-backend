package dev.brooskiey.taskmodule.repositories;

import dev.brooskiey.taskmodule.models.TaskType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskTypeRepo extends CrudRepository<TaskType, Long> {
}
