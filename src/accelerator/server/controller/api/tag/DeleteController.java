package accelerator.server.controller.api.tag;

import org.slim3.controller.Navigation;

import accelerator.server.controller.ApplicationController;
import accelerator.server.meta.TagMeta;
import accelerator.server.service.TagServiceImpl;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class DeleteController extends ApplicationController {
    private final TagMeta meta = TagMeta.get();
    private final TagServiceImpl service = new TagServiceImpl();

    @Override
    public Navigation doPost() throws Exception {
        Key key = asKey(meta.key);
        service.deleteTag(key);
        response.getWriter().write(KeyFactory.keyToString(key));
        return null;
    }
}
