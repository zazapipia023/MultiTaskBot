package ru.zaza.multitaskbot.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import ru.zaza.multitaskbot.entities.Client;
import ru.zaza.multitaskbot.repositories.ClientRepository;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("classpath:data.sql")
public class DatabaseTest {

    @Autowired
    ClientRepository clientRepository;

    @Test
    public void testFindById() {

        Optional<Client> foundClient = clientRepository.findById(194242345L);

        Assertions.assertNotNull(foundClient.orElse(null));
        Client client = foundClient.get();
        Assertions.assertEquals("some_action", client.getAction());
        client.setAction("another_action");
        clientRepository.save(client);

        foundClient = clientRepository.findById(194242345L);
        Assertions.assertEquals("another_action", foundClient.get().getAction());
    }

}
