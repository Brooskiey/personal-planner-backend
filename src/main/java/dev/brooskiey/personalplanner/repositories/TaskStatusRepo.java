package dev.brooskiey.personalplanner.repositories;

import dev.brooskiey.personalplanner.models.TaskStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskStatusRepo extends CrudRepository<TaskStatus, Long> {

    TaskStatus findById(long id);

    TaskStatus findByName(String name);


}
