package ru.zaza.multitaskbot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zaza.multitaskbot.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
