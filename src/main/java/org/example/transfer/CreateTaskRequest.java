package org.example.transfer;

import java.time.LocalDate;

//DTO (data transfer object)
public class CreateTaskRequest {

  private String description;
  private LocalDate deadline;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDate getDeadline() {
    return deadline;
  }

  public void setDeadline(LocalDate deadline) {
    this.deadline = deadline;
  }

  @Override
  public String toString() {
    return "CreateTaskRequest{" +
        "description='" + description + '\'' +
        ", deadline=" + deadline +
        '}';
  }
}
