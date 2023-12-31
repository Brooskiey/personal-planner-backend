package dev.brooskiey.taskmodule.repositories;

import dev.brooskiey.taskmodule.models.TypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepo extends CrudRepository<TypeEntity, Long> {
}
