package dev.brooskiey.taskmodule.repositories;

import dev.brooskiey.taskmodule.TaskModuleApplication;
import dev.brooskiey.taskmodule.models.StatusEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest(classes= TaskModuleApplication.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StatusEntityRepoTest {

    @Autowired
    StatusRepo statusRepo;

    StatusEntity status;

    // Setup for the tests
    @BeforeAll
    void setup() {
        status = new StatusEntity(0, "not started");
    }

    // Save the status
    @Order(1)
    @Test
    void saveStatus_Success() {
        status = statusRepo.save(status);
        Assertions.assertNotEquals(0, status.getId());
    }

    // Find status by Id
    @Order(2)
    @Test
    void findById_Success() {
        StatusEntity foundStatusEntity = statusRepo.findById(status.getId());
        Assertions.assertEquals(status.getId(), foundStatusEntity.getId());
    }

    // Find status by name
    @Order(3)
    @Test
    void findByName_Success() {
        StatusEntity foundStatusEntity = statusRepo.findByName(status.getName());
        Assertions.assertEquals(status.getName(), foundStatusEntity.getName());
    }

    // Find all statuses
    @Order(4)
    @Test
    void findAll_Success() {
        List<StatusEntity> statusEntities = (List<StatusEntity>) statusRepo.findAll();
        Assertions.assertNotEquals(0, statusEntities.size());
    }

    // Update a status
    @Order(5)
    @Test
    void updateStatus_Success() {
        status.setName("COMPLETED");
        StatusEntity updatedStatusEntity = statusRepo.save(status);
        Assertions.assertNotEquals("not started", updatedStatusEntity.getName());
    }

    // Delete a status
    @Order(6)
    @Test
    void deleteStatus_Success() {
        statusRepo.deleteById(status.getId());
        List<StatusEntity> statusEntities = (List<StatusEntity>) statusRepo.findAll();
        Assertions.assertEquals(0, statusEntities.size());
    }

    // Teardown
    @AfterAll
    void teardown() {
        statusRepo.deleteAll();
        Assertions.assertEquals(0, ((List<StatusEntity>) statusRepo.findAll()).size());
    }
}
