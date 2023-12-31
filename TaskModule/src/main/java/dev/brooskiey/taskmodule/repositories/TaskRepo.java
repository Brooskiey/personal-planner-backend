package dev.brooskiey.taskmodule.repositories;

import dev.brooskiey.taskmodule.models.TaskEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepo extends CrudRepository<TaskEntity, Long> {

    TaskEntity findById(long id);

    List<TaskEntity> findByDateInitiated(LocalDate date);

    List<TaskEntity> findByRecurrenceId(long id);

    List<TaskEntity> findByName(String name);

    @Query("SELECT t FROM TaskEntity t WHERE t.type.name like ?1")
    List<TaskEntity> findByType(String type);
}
