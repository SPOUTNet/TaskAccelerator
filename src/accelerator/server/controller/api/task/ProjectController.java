package accelerator.server.controller.api.task;

import java.util.List;

import org.slim3.controller.Navigation;

import accelerator.server.controller.ApplicationController;
import accelerator.server.controller.validator.Validators;
import accelerator.server.meta.TaskMeta;
import accelerator.server.service.TaskServiceImpl;
import accelerator.shared.model.Task;

import com.google.appengine.api.datastore.Key;

public class ProjectController extends ApplicationController {
    private final TaskServiceImpl service = new TaskServiceImpl();
    private final TaskMeta meta = TaskMeta.get();

    @Override
    public Navigation doGet() throws Exception {
        validate();

        Key projectKey = asKey("projectKey");
        List<Task> tasks = service.getProjectTaskList(projectKey);
        String json = meta.modelsToJson(tasks.toArray());
        response.getWriter().write(json);
        return null;
    }

    private void validate() throws Exception {
        Validators v = new Validators(request);
        v.add("projectKey", v.required(), v.keyType());
        if (v.validate() == false) {
            throw new Exception(v.getErrorMessage());
        }
    }
}
