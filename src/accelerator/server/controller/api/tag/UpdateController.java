package accelerator.server.controller.api.tag;

import org.slim3.controller.Navigation;

import accelerator.server.controller.ApplicationController;
import accelerator.server.meta.TagMeta;
import accelerator.server.service.TagServiceImpl;
import accelerator.shared.model.Tag;

import com.google.appengine.api.datastore.Key;

public class UpdateController extends ApplicationController {
    private final TagMeta meta = TagMeta.get();
    private final TagServiceImpl service = new TagServiceImpl();

    @Override
    public Navigation doPost() throws Exception {
        Key key = asKey(meta.key);
        Tag target = service.getTag(key);
        target.setName(asString(meta.name));

        Tag result = service.updateTag(target);

        String json = meta.modelToJson(result);
        response.getWriter().write(json);
        return null;
    }
}
