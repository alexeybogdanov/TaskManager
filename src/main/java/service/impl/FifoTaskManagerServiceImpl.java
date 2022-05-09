package service.impl;

import model.Process;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class FifoTaskManagerServiceImpl extends BaseTaskManagerServiceImpl {

  private final BlockingQueue<Process> processQueue = new ArrayBlockingQueue<>(MAX_CAPACITY);

  public FifoTaskManagerServiceImpl(int capacity) {
    super(capacity);
  }

  @Override
  public synchronized void add(Process process) throws Exception {
    boolean addedSuccessfully = processQueue.offer(process);
    if (!addedSuccessfully) {
      Process processToRemove = processQueue.remove();
      kill(processToRemove);
      processQueue.offer(process);
    }
    super.add(process);
  }
}

