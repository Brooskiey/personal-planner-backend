package dev.brooskiey.personalplanner.repositories;

import dev.brooskiey.personalplanner.models.RecurrTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecurrTaskRepo extends CrudRepository<RecurrTask, Long> {

    RecurrTask findById(long id);
}
