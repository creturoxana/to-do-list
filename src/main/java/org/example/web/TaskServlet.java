package org.example.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.example.config.ObjectMapperConfiguration;
import org.example.domain.Task;
import org.example.service.TaskService;
import org.example.transfer.CreateTaskRequest;
import org.example.transfer.UpdateTaskRequest;

@WebServlet("/tasks")
public class TaskServlet extends HttpServlet {

  private TaskService taskService = new TaskService();

  //endpoint
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    addCorsHeaders(resp);

    CreateTaskRequest request = ObjectMapperConfiguration.OBJECT_MAPPER.readValue(req.getReader(),
        CreateTaskRequest.class);

    try {
      taskService.createTask(request);
    } catch (SQLException | ClassNotFoundException e) {
      resp.sendError(500, "There was an error while processing the request." + e.getMessage());
    }
  }

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    addCorsHeaders(resp);

    String id = req.getParameter("id");

    UpdateTaskRequest request = ObjectMapperConfiguration.OBJECT_MAPPER.readValue(req.getReader(),
        UpdateTaskRequest.class);

    try {
      taskService.updateTask(Long.parseLong(id),request);
    } catch (SQLException | ClassNotFoundException e) {
      resp.sendError(500, "There was an error while processing the request." + e.getMessage());
    }
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    addCorsHeaders(resp);

    String id = req.getParameter("id");

    try {
      taskService.deleteTask(Long.parseLong(id));
    } catch (SQLException | ClassNotFoundException e) {
      resp.sendError(500, "There was an error while processing the request." + e.getMessage());
    }
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    addCorsHeaders(resp);

    try {
      List<Task> tasks = taskService.getTasks();
      ObjectMapperConfiguration.OBJECT_MAPPER.writeValue(resp.getWriter(),tasks);

    } catch (SQLException | ClassNotFoundException e) {
      resp.sendError(500, "There was an error while processing the request." + e.getMessage());
    }
  }

  //preflight request
  @Override
  protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    addCorsHeaders(resp);
  }

  private void addCorsHeaders(HttpServletResponse resp) {
    resp.addHeader("Access-Control-Allow-Origin","*");
    resp.addHeader("Access-Control-Allow-Methods","POST, GET, PUT, DELETE");
    resp.addHeader("Access-Control-Allow-Headers","content-type");

  }
}
