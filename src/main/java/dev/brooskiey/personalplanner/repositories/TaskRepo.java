package dev.brooskiey.personalplanner.repositories;

import dev.brooskiey.personalplanner.models.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.util.List;

@Repository
public interface TaskRepo extends CrudRepository<Task, Long> {

    Task findById(long id);

    List<Task> findByDateInitiated(Date date);

    List<Task> findByRecurrenceId(long id);

}
