package model;

import java.time.LocalDateTime;

public class ProcessDto {
  private final String pid;
  private final Priority priority;
  private final LocalDateTime createdAt;

  public ProcessDto(Process process) {
    this.pid = process.getPid();
    this.priority = process.getPriority();
    this.createdAt = LocalDateTime.now();
  }

  public String getPid() {
    return pid;
  }

  public Priority getPriority() {
    return priority;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public Process convertToProcess() {
    return new Process(pid, priority);
  }
}

