package service.impl;

import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.logging.Logger;
import model.Priority;
import model.Process;
import model.ProcessDto;

public class PriorityTaskManagerServiceImpl extends BaseTaskManagerServiceImpl {

  private final static Logger LOGGER = Logger.getLogger(PriorityTaskManagerServiceImpl.class.getName());

  private final Comparator<ProcessDto> priorityTimeComparator = Comparator.comparing(ProcessDto::getPriority)
      .reversed().thenComparing(ProcessDto::getCreatedAt);
  private final BlockingQueue<ProcessDto> priorityProcessQueue =
      new PriorityBlockingQueue<>(MAX_CAPACITY, priorityTimeComparator);

  public PriorityTaskManagerServiceImpl(int capacity) {
    super(capacity);
  }


  @Override
  public synchronized void add(Process process) throws Exception {
    ProcessDto processDto = new ProcessDto(process);
    boolean isMaxSizeReached = priorityProcessQueue.size() == MAX_CAPACITY;
    if (!isMaxSizeReached) {
      priorityProcessQueue.offer(processDto);
      super.add(process);
    } else {
      Priority newProcessPriority = process.getPriority();
      ProcessDto headProcess = priorityProcessQueue.peek();

      if (headProcess != null && headProcess.getPriority() !=null
          && newProcessPriority.getPriorityWeight() < headProcess.getPriority().getPriorityWeight()) {
        ProcessDto processDtoToRemove = priorityProcessQueue.remove();
        kill(processDtoToRemove.convertToProcess());
        priorityProcessQueue.offer(processDto);
        super.add(process);
      } else {
        LOGGER.warning(String.format("Skip process with PID: '%s'", process.getPid()));
      }
    }
  }
}

