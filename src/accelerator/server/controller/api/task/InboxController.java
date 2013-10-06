package accelerator.server.controller.api.task;

import java.util.List;

import org.slim3.controller.Navigation;

import accelerator.server.controller.ApplicationController;
import accelerator.server.meta.TaskMeta;
import accelerator.server.service.TaskServiceImpl;
import accelerator.shared.model.Task;

public class InboxController extends ApplicationController {
    private final TaskServiceImpl service = new TaskServiceImpl();
    private final TaskMeta meta = TaskMeta.get();

    @Override
    public Navigation doGet() throws Exception {
        List<Task> tasks = service.getInboxTaskList();
        String json = meta.modelsToJson(tasks.toArray());
        response.getWriter().write(json);
        return null;
    }
}
