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
import com.google.appengine.api.datastore.KeyFactory;

public class UpdateControllerTest extends ControllerTestCase {
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
    public void 更新したタグをJSONで返すべき() throws Exception {
        Tag tag = new Tag();
        tag.setName("bar");
        Key key = Datastore.put(tag);

        tester.request.setMethod("POST");
        tester.param(meta.name, "foo");
        tester.param(meta.key, KeyFactory.keyToString(key));
        tester.start("/api/tag/update");

        String json = tester.response.getOutputAsString();
        Tag actual = meta.jsonToModel(json);
        assertThat(actual.getName(), is("foo"));
    }

    @Test
    public void 更新したタグがDatastoreに保存されているべき() throws Exception {
        Tag tag = new Tag();
        tag.setName("bar");
        Key key = Datastore.put(tag);

        tester.request.setMethod("POST");
        tester.param(meta.name, "foo");
        tester.param(meta.key, KeyFactory.keyToString(key));
        tester.start("/api/tag/update");

        Tag actual = Datastore.get(meta, key);
        assertThat(actual.getName(), is("foo"));
    }
}
