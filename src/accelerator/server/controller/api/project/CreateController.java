package accelerator.server.controller.api.project;

import org.slim3.controller.Navigation;

import accelerator.server.controller.ApplicationController;
import accelerator.server.meta.ProjectMeta;
import accelerator.server.service.ProjectServiceImpl;
import accelerator.shared.model.Project;

public class CreateController extends ApplicationController {
    private final ProjectServiceImpl service = new ProjectServiceImpl();
    private final ProjectMeta meta = ProjectMeta.get();

    @Override
    protected Navigation doPost() throws Exception {
        Project project = new Project();
        project.setName(asString(meta.name));
        service.createProject(project);

        String json = meta.modelToJson(project);
        response.getWriter().write(json);
        return null;
    }
}
