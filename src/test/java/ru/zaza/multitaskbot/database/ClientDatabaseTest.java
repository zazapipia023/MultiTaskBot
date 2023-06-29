package ru.zaza.multitaskbot.database;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import ru.zaza.multitaskbot.entities.Client;
import ru.zaza.multitaskbot.repositories.ClientRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("classpath:client_data.sql")
public class ClientDatabaseTest {

    @Autowired
    ClientRepository clientRepository;

    @Test
    public void testFindById() {
        Client foundClient = clientRepository.findById(194242345L).get();

        assertThat(foundClient.getId()).isEqualTo(194242345L);
        assertThat(foundClient.getAction()).isEqualTo("some_action");
    }

    @Test
    public void testCreateClient() {
        Client createdClient = new Client();
        createdClient.setId(294532535L);
        clientRepository.save(createdClient);

        Client foundClient = clientRepository.findById(294532535L).get();
        assertThat(foundClient.getId()).isEqualTo(createdClient.getId());
    }

    @Test
    public void testUpdatedClient() {
        Client clientToUpdate = clientRepository.findById(194242345L).get();
        clientToUpdate.setAction("new_action");
        clientRepository.save(clientToUpdate);

        Client updatedClient = clientRepository.findById(194242345L).get();
        assertThat(updatedClient.getAction()).isEqualTo("new_action");
    }

    @Test
    public void testDeleteClient() {
        Client clientToDelete = clientRepository.findById(194242345L).get();
        clientRepository.delete(clientToDelete);

        Client foundClient = clientRepository.findById(194242345L).orElse(null);
        assertThat(foundClient).isNull();
    }
}
