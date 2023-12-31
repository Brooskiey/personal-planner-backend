package dev.brooskiey.taskmodule.repositories;

import dev.brooskiey.taskmodule.models.RecurrTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecurrTaskRepo extends CrudRepository<RecurrTask, Long> {

    RecurrTask findById(long id);

    RecurrTask findByRecurrence(String recurrence);
}
