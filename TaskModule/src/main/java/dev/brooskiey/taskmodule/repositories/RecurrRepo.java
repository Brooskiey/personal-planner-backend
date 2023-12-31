package dev.brooskiey.taskmodule.repositories;

import dev.brooskiey.taskmodule.models.RecurrenceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecurrRepo extends CrudRepository<RecurrenceEntity, Long> {

    RecurrenceEntity findById(long id);

    RecurrenceEntity findByRecurrence(String recurrence);
}
