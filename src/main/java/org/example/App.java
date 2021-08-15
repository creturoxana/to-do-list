package org.example;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import org.example.persistance.TaskRepository;
import org.example.transfer.CreateTaskRequest;

/**
 * Hello world!
 */
public class App {

  public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {

    CreateTaskRequest request = new CreateTaskRequest();
    request.setDescription("Learn JDBC");
    request.setDeadline(LocalDate.now().plusWeeks(2));

    TaskRepository taskRepository = new TaskRepository();
    taskRepository.createTask(request);
  }
}
