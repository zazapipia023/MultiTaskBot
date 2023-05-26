package ru.zaza.multitaskbot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zaza.multitaskbot.entities.Client;
import ru.zaza.multitaskbot.repositories.ClientRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client findOne(Long id) {
        Optional<Client> foundUser = clientRepository.findById(id);
        return foundUser.orElse(null);
    }

    @Transactional
    public void save(Client client) {
        clientRepository.save(client);
    }
}
