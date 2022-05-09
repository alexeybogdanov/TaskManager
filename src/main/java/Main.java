import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;
import service.TaskManagerService;
import service.impl.BaseTaskManagerServiceImpl;
import service.impl.FifoTaskManagerServiceImpl;
import service.impl.PriorityTaskManagerServiceImpl;


public class Main {

  private final static Logger LOGGER = Logger.getLogger(Main.class.getName());

  public static void main(String[] args) {

    try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
      Properties prop = new Properties();
      prop.load(input);

      int size = Integer.parseInt(prop.getProperty("storage.size"));
      String approach = prop.getProperty("task.manager.approach");
      LOGGER.info(String.format("Starting Task Manager with max size=%s", size));
      LOGGER.info(String.format("Starting Task Manager with approach=%s", approach));

      TaskManagerService taskManager;
      switch(approach) {
        case "fifo":
          taskManager = new FifoTaskManagerServiceImpl(size);
          break;
        case "prio":
          taskManager = new PriorityTaskManagerServiceImpl(size);
          break;
        default:
          taskManager = new BaseTaskManagerServiceImpl(size);
      }
    } catch (IOException io) {
      io.printStackTrace();
    }
  }
}
