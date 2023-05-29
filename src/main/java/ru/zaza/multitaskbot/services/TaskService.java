package ru.zaza.multitaskbot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zaza.multitaskbot.entities.Task;
import ru.zaza.multitaskbot.repositories.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task findOne(int id) {
        Optional<Task> foundTask = taskRepository.findById(id);
        return foundTask.orElse(null);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Transactional
    public void save(Task task) {
        taskRepository.save(task);
    }

    @Transactional
    public void delete(Task task) {
        taskRepository.delete(task);
    }

    @Transactional
    public void deleteAll() {
        taskRepository.deleteAll();
    }
}
