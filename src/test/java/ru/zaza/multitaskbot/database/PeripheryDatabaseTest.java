package ru.zaza.multitaskbot.database;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import ru.zaza.multitaskbot.entities.Periphery;
import ru.zaza.multitaskbot.repositories.PeripheryRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("classpath:periphery_data.sql")
public class PeripheryDatabaseTest {

    @Autowired
    PeripheryRepository peripheryRepository;

    @Test
    public void testFindBySerialNumber() {
        Periphery foundPeriphery = peripheryRepository.findBySerialNumber("12345ABC");

        assertThat(foundPeriphery.getName()).isEqualTo("Test name");
        assertThat(foundPeriphery.getSerialNumber()).isEqualTo("12345ABC");
        assertThat(foundPeriphery.getIsRepairing()).isFalse();
    }

    @Test
    public void testCreatePeriphery() {
        Periphery createdPeriphery = new Periphery();
        createdPeriphery.setName("Test name 2");
        createdPeriphery.setSerialNumber("4325425KG");
        createdPeriphery.setIsRepairing(true);
        peripheryRepository.save(createdPeriphery);

        Periphery foundPeriphery = peripheryRepository.findBySerialNumber("4325425KG");
        assertThat(foundPeriphery.getName()).isEqualTo("Test name 2");
        assertThat(foundPeriphery.getSerialNumber()).isEqualTo("4325425KG");
        assertThat(foundPeriphery.getIsRepairing()).isTrue();
    }

    @Test
    public void testUpdatedPeriphery() {
        Periphery peripheryToUpdate = peripheryRepository.findBySerialNumber("12345ABC");
        peripheryToUpdate.setIsRepairing(true);
        peripheryRepository.save(peripheryToUpdate);

        Periphery foundPeriphery = peripheryRepository.findBySerialNumber("12345ABC");
        assertThat(foundPeriphery.getIsRepairing()).isTrue();
    }

    @Test
    public void testDeletePeriphery() {
        Periphery peripheryToDelete = peripheryRepository.findBySerialNumber("12345ABC");
        peripheryRepository.delete(peripheryToDelete);

        Periphery foundPeriphery = peripheryRepository.findBySerialNumber("12345ABC");
        assertThat(foundPeriphery).isNull();
    }
}
