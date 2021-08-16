package org.example.persistance;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.example.domain.Task;
import org.example.transfer.CreateTaskRequest;
import org.example.transfer.UpdateTaskRequest;

public class TaskRepository {

  public void createTask(CreateTaskRequest request)
      throws IOException, SQLException, ClassNotFoundException {
    String sql = "INSERT INTO task (description, deadline) VALUES (?, ?)";

    try (Connection connection = DatabaseConfiguration.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

      preparedStatement.setString(1, request.getDescription());
      preparedStatement.setDate(2, Date.valueOf(request.getDeadline()));

      preparedStatement.executeUpdate();
    }
  }

  public void updateTask(long id, UpdateTaskRequest request)
      throws SQLException, IOException, ClassNotFoundException {
    String sql = "UPDATE task SET done=? WHERE id=?";

    try (Connection connection = DatabaseConfiguration.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

      preparedStatement.setBoolean(1, request.isDone());
      preparedStatement.setLong(2, id);

      preparedStatement.executeUpdate();
    }
  }

  public void deleteTask(long id) throws SQLException, IOException, ClassNotFoundException {
    String sql = "DELETE FROM task WHERE id = ?";
    try (Connection connection = DatabaseConfiguration.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

      preparedStatement.setLong(1, id);

      preparedStatement.executeUpdate();
    }
  }

  public List<Task> getTasks() throws SQLException, IOException, ClassNotFoundException {
    String sql = "SELECT id, description, deadline, done FROM task";

    List<Task> tasks = new ArrayList<>();

    try (Connection connection = DatabaseConfiguration.getConnection();
        Statement statement = connection.createStatement()) {

      ResultSet resultSet = statement.executeQuery(sql);

      while (resultSet.next()) {
        Task task = new Task();
        task.setId(resultSet.getLong("id"));
        task.setDescription(resultSet.getString("description"));

        Date deadlineAsSqlDate = resultSet.getDate("deadline");
        task.setDeadline(deadlineAsSqlDate.toLocalDate());

        task.setDone(resultSet.getBoolean("done"));

        tasks.add(task);
      }
    }

    return tasks;
  }
}
