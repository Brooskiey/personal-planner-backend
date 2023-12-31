package dev.brooskiey.taskmodule.repositories;

import dev.brooskiey.taskmodule.models.StatusEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepo extends CrudRepository<StatusEntity, Long> {

    StatusEntity findById(long id);

    StatusEntity findByName(String name);


}
