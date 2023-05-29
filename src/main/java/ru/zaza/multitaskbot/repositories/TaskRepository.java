package ru.zaza.multitaskbot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zaza.multitaskbot.entities.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
}
