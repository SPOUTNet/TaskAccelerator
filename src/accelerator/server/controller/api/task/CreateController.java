package accelerator.server.controller.api.task;

import org.slim3.controller.Navigation;
import org.slim3.util.RequestMap;

import accelerator.server.controller.ApplicationController;
import accelerator.server.controller.validator.Validators;
import accelerator.server.meta.TaskMeta;
import accelerator.server.service.TaskServiceImpl;
import accelerator.shared.model.Task;

public class CreateController extends ApplicationController {
    private final TaskServiceImpl service = new TaskServiceImpl();
    private final TaskMeta meta = TaskMeta.get();

    @Override
    public Navigation doPost() throws Exception {
        Validators v = new Validators(new RequestMap(request));
        v.add(meta.name, v.required());
        v.add(meta.project, v.keyType());
        v.add(meta.dueDate, v.dateType("yyyy/MM/dd"));
        if (v.validate() == false) {
            throw new Exception(v.getErrorMessage());
        }

        Task task = buildTaskFromRequest();
        Task result = service.createTask(task);

        String json = meta.modelToJson(result);
        response.getWriter().write(json);
        return null;
    }

    private Task buildTaskFromRequest() {
        Task task = new Task();
        task.setName(asString(meta.name));
        if (param(meta.project) != null) {
            task.setProject(asKey(meta.project));
        }
        if (param(meta.dueDate) != null) {
            task.setDueDate(asDate(meta.dueDate, "yyyy/MM/dd"));
        }
        if (param(meta.tags) != null) {
            task.setTags(asKeyList(meta.tags));
        }
        return task;
    }
}