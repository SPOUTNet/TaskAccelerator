package accelerator.server.controller.api.task;

import java.util.Date;
import java.util.List;

import org.slim3.controller.Navigation;

import accelerator.server.controller.ApplicationController;
import accelerator.server.meta.TaskMeta;
import accelerator.server.service.TaskServiceImpl;
import accelerator.shared.model.Task;

public class TodayController extends ApplicationController {
    private final TaskServiceImpl service = new TaskServiceImpl();
    private final TaskMeta meta = TaskMeta.get();

    @Override
    public Navigation doGet() throws Exception {
        Date today = asDate("today", "yyyy/MM/dd");
        List<Task> tasks = service.getTodayTaskList(today);
        String json = meta.modelsToJson(tasks.toArray());
        response.getWriter().write(json);
        return null;
    }
}
