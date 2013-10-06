package accelerator.server.controller.api.tag;

import org.slim3.controller.Navigation;

import accelerator.server.controller.ApplicationController;
import accelerator.server.meta.TagMeta;
import accelerator.server.service.TagServiceImpl;
import accelerator.shared.model.Tag;

public class CreateController extends ApplicationController {
    private final TagMeta meta = TagMeta.get();
    private final TagServiceImpl service = new TagServiceImpl();

    @Override
    public Navigation doPost() throws Exception {
        Tag tag = new Tag();
        tag.setName(asString(meta.name));

        Tag result = service.createTag(tag);

        String json = meta.modelToJson(result);
        response.getWriter().write(json);
        return null;
    }
}
