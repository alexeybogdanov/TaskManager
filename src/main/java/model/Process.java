package model;

public class Process {

  private final String pid;
  private final Priority priority;

  public Process(String pid, Priority priority) {
    this.pid = pid;
    this.priority = priority;
  }

  public boolean kill() {
    return true;
  }

  public String getPid() {
    return pid;
  }

  public Priority getPriority() {
    return priority;
  }

  @Override
  public String toString() {
    return "Process{" +
        "pid='" + pid + '\'' +
        ", priority='" + priority + '\'' +
        '}';
  }
}
