package dev.brooskiey.taskmodule.repositories;

import dev.brooskiey.taskmodule.models.TaskStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskStatusRepo extends CrudRepository<TaskStatus, Long> {

    TaskStatus findById(long id);

    TaskStatus findByName(String name);


}
