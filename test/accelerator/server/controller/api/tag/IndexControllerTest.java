package accelerator.server.controller.api.tag;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slim3.datastore.Datastore;
import org.slim3.tester.ControllerTestCase;

import accelerator.server.meta.TagMeta;
import accelerator.shared.model.Tag;

public class IndexControllerTest extends ControllerTestCase {
    private static final TagMeta meta = TagMeta.get();

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();

        // ログイン
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs
            .put("com.google.appengine.api.users.UserService.user_id_key", "1");
        tester.environment.setAttributes(attrs);
        tester.environment.setEmail("example@gmail.com");
    }

    @Test
    public void run() throws Exception {
        Tag t1 = new Tag();
        t1.setName("foo");
        Datastore.put(t1);

        Tag t2 = new Tag();
        t2.setName("bar");
        Datastore.put(t2);

        tester.start("/api/tag/");
        IndexController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));

        String json = tester.response.getOutputAsString();
        Tag[] tags = meta.jsonToModels(json);
        assertThat(tags.length, is(2));
    }
}
