package service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import model.ProcessDto;
import model.Process;
import service.TaskManagerService;

public class BaseTaskManagerServiceImpl implements TaskManagerService {

  private final static Logger LOGGER = Logger.getLogger(BaseTaskManagerServiceImpl.class.getName());

  private final Map<String, ProcessDto> processDtoMapStorage = new ConcurrentHashMap<>();

  protected final int MAX_CAPACITY;

  public BaseTaskManagerServiceImpl(int capacity) {
    this.MAX_CAPACITY = capacity;
  }

  @Override
  public synchronized void add(Process process) throws Exception {
    if (processDtoMapStorage.size() >=  MAX_CAPACITY) {
      throw new RuntimeException(String.format("Max process storage capacity reached. Maximum size is: %s,"
                                                   + " current size is %s", MAX_CAPACITY, processDtoMapStorage.size()));
    }

    if (processDtoMapStorage.containsKey(process.getPid())) {
      throw new Exception(String.format("Process with pid: '%s' already exists", process.getPid()));
    }
    processDtoMapStorage.put(process.getPid(), new ProcessDto(process));
  }

  @Override
  public List<Process> listByTime() {
    return processDtoMapStorage.values().stream()
        .sorted(Comparator.comparing(ProcessDto::getCreatedAt))
        .map(processDto -> new Process(processDto.getPid(), processDto.getPriority()))
        .collect(Collectors.toList());
  }

  @Override
  public List<Process> listByPriority() {
    return processDtoMapStorage.values().stream()
        .sorted(Comparator.comparing(processDto -> processDto.getPriority().getPriorityWeight()))
        .map(ProcessDto::convertToProcess)
        .collect(Collectors.toList());
  }

  @Override
  public List<Process> listById() {
    return processDtoMapStorage.values().stream()
        .sorted(Comparator.comparing(ProcessDto::getPid))
        .map(ProcessDto::convertToProcess)
        .collect(Collectors.toList());
  }

  @Override
  public void kill(Process process) {
    if (process.kill()) {
      processDtoMapStorage.remove(process.getPid());
      LOGGER.warning(String.format("Kill process with PID: '%s'", process.getPid()));
    }
  }
}
