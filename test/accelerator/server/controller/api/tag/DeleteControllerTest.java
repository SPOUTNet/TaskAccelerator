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

public class DeleteControllerTest extends ControllerTestCase {
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
    public void 削除したタグのキーを返すべき() throws Exception {
        Tag tag = new Tag();
        tag.setName("foo");
        Key key = Datastore.put(tag);

        tester.request.setMethod("POST");
        tester.param(meta.key, KeyFactory.keyToString(key));
        tester.start("/api/tag/delete");

        String s = tester.response.getOutputAsString();
        Key actual = KeyFactory.stringToKey(s);
        assertThat(actual, is(key));
    }

    @Test
    public void 指定したタグがDatastoreから削除されているべき() throws Exception {
        Tag tag = new Tag();
        tag.setName("foo");
        Key key = Datastore.put(tag);

        tester.request.setMethod("POST");
        tester.param(meta.key, KeyFactory.keyToString(key));
        tester.start("/api/tag/delete");

        assertThat(Datastore.query(meta).count(), is(0));
    }
}
