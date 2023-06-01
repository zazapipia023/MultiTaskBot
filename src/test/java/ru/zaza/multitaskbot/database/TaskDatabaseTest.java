package ru.zaza.multitaskbot.database;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import ru.zaza.multitaskbot.entities.Task;
import ru.zaza.multitaskbot.repositories.TaskRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("classpath:task_data.sql")
public class TaskDatabaseTest {

    @Autowired
    TaskRepository taskRepository;


    @Test
    public void testFindAll() {
        List<Task> tasks = taskRepository.findAll();

        assertThat(tasks).isNotEmpty();

        for (Task task:
             tasks) {
            assertThat(task).isNotNull();
        }
    }

    @Test
    public void testCreateTask() {
        Task createdTask = new Task();
        createdTask.setTask("Make some moves");
        taskRepository.save(createdTask);

        List<Task> tasks = taskRepository.findAll();


        Task foundTask = taskRepository.findById(tasks.get(tasks.size() - 1).getId()).get();
        assertThat(foundTask.getTask()).isEqualTo("Make some moves");
    }

    @Test
    public void testDeleteTask() {
        Task taskToDelete = taskRepository.findAll().get(0);

        taskRepository.delete(taskToDelete);

        Task foundTask = taskRepository.findById(taskToDelete.getId()).orElse(null);
        assertThat(foundTask).isNull();
    }
}
