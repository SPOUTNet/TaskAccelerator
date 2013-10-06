package accelerator.server.controller.api.project;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import accelerator.server.meta.ProjectMeta;
import accelerator.server.service.ProjectServiceImpl;
import accelerator.server.util.HttpStatus;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class DeleteController extends Controller {
    private final ProjectMeta meta = ProjectMeta.get();
    private final ProjectServiceImpl service = new ProjectServiceImpl();

    @Override
    public Navigation run() throws Exception {
        if (isPost()) {
            Key key = asKey(meta.key);
            service.deleteProject(key);
            response.getWriter().write(KeyFactory.keyToString(key));
        } else {
            response.setStatus(HttpStatus.MethodNotAllowed);
        }
        return null;
    }
}
