package service.impl;

import static org.junit.jupiter.api.Assertions.*;

import model.Priority;
import model.Process;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.TaskManagerService;

class BaseTaskManagerServiceImplTest {
  TaskManagerService taskManager;
  Process p1;
  Process p2;
  Process p3;
  Process p4;
  Process p5;
  Process p6;
  Process p7;

  @BeforeEach
  void setupManager(){
    taskManager = new BaseTaskManagerServiceImpl(5);
    p1 = new Process("1", Priority.HIGH);
    p2 = new Process("2", Priority.LOW);
    p3 = new Process("3", Priority.MEDIUM);
    p4 = new Process("4", Priority.HIGH);
    p5 = new Process("5", Priority.MEDIUM);
    p6 = new Process("6", Priority.HIGH);
    p7 = new Process("7", Priority.LOW);
  }

  @Test
  void add_process_not_exceed_storage_size_success() throws Exception {
    taskManager.add(p1);
    taskManager.add(p2);
    taskManager.add(p3);
    taskManager.add(p4);
    taskManager.add(p5);
    Assertions.assertEquals(5 , taskManager.listById().size());
  }

  @Test
  void add_process_exceed_storage_size_throws_exception(){
    Exception exception = assertThrows(RuntimeException.class, () -> {
      taskManager.add(p1);
      taskManager.add(p2);
      taskManager.add(p3);
      taskManager.add(p4);
      taskManager.add(p5);
      taskManager.add(p6);
    });

    String expectedMessage = "Max process storage capacity reached";
    Assertions.assertTrue(exception.getMessage().contains(expectedMessage));  }
}
