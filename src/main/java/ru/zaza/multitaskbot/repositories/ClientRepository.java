package ru.zaza.multitaskbot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zaza.multitaskbot.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
