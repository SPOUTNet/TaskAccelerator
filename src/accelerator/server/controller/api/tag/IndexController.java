package accelerator.server.controller.api.tag;

import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import accelerator.server.meta.TagMeta;
import accelerator.server.service.TagServiceImpl;
import accelerator.shared.model.Tag;

public class IndexController extends Controller {
    private final TagMeta meta = TagMeta.get();
    private final TagServiceImpl service = new TagServiceImpl();

    @Override
    public Navigation run() throws Exception {
        List<Tag> tags = service.getTagList();
        String json = meta.modelsToJson(tags.toArray());
        response.getWriter().write(json);
        return null;
    }
}
