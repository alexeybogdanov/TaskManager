package service;

import java.util.List;
import model.Process;

public interface TaskManagerService {

  void add(Process process) throws Exception;

  List<Process> listByTime();

  List<Process> listByPriority();

  List<Process> listById();

  void kill(Process process);

}
