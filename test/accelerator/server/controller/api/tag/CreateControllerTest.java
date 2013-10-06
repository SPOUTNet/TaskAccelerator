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

import com.google.appengine.api.datastore.Key;

public class CreateControllerTest extends ControllerTestCase {
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
    public void 作成したタグをJSONで返すべき() throws Exception {
        tester.request.setMethod("POST");
        tester.param(meta.name, "foo");
        tester.start("/api/tag/create");

        String json = tester.response.getOutputAsString();
        Tag tag = meta.jsonToModel(json);
        assertThat(tag.getName(), is("foo"));
    }

    @Test
    public void 作成したタグがDatastoreに保存されているべき() throws Exception {
        tester.request.setMethod("POST");
        tester.param(meta.name, "foo");
        tester.start("/api/tag/create");

        String json = tester.response.getOutputAsString();
        Tag tag = meta.jsonToModel(json);
        Key key = tag.getKey();

        Tag actual = Datastore.get(meta, key);
        assertThat(actual.getName(), is("foo"));
    }
}
