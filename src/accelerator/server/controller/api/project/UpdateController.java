package accelerator.server.controller.api.project;

import org.slim3.controller.Navigation;

import accelerator.server.controller.ApplicationController;
import accelerator.server.meta.ProjectMeta;
import accelerator.server.service.ProjectServiceImpl;
import accelerator.shared.model.Project;

import com.google.appengine.api.datastore.Key;

public class UpdateController extends ApplicationController {
    private ProjectServiceImpl service = new ProjectServiceImpl();
    private ProjectMeta meta = ProjectMeta.get();

    @Override
    protected final Navigation doPost() throws Exception {
        Key key = asKey(meta.key);
        Project target = service.getProject(key);
        target.setName(asString(meta.name));
        Project project = service.updateProject(target);

        String json = meta.modelToJson(project);
        response.getWriter().write(json);
        return null;
    }
}
