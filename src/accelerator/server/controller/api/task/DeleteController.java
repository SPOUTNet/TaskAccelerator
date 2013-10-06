package accelerator.server.controller.api.task;

import java.util.List;

import org.slim3.controller.Navigation;

import accelerator.server.controller.ApplicationController;
import accelerator.server.meta.TaskMeta;
import accelerator.server.service.TaskServiceImpl;

import com.google.appengine.api.datastore.Key;

public class DeleteController extends ApplicationController {
    private final TaskServiceImpl service = new TaskServiceImpl();
    private final TaskMeta meta = TaskMeta.get();

    @Override
    public Navigation doPost() throws Exception {
        List<Key> keys = asKeyList(meta.key);
        service.deleteTask(keys);
        return null;
    }
}
