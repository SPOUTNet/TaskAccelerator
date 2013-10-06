package accelerator.server.controller.api.task;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slim3.datastore.Datastore;
import org.slim3.tester.ControllerTestCase;

import accelerator.shared.model.Task;

import com.google.appengine.api.datastore.KeyFactory;

public class DeleteControllerTest extends ControllerTestCase {
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
    public void 指定したタスクを削除できるべき() throws Exception {
        // テストデータ作成
        Task foo = new Task();
        foo.setName("foo");
        Datastore.put(foo);
        Task bar = new Task();
        bar.setName("bar");
        Datastore.put(bar);

        // API 呼び出し
        String[] keys = new String[2];
        keys[0] = KeyFactory.keyToString(foo.getKey());
        keys[1] = KeyFactory.keyToString(bar.getKey());
        tester.request.setMethod("POST");
        tester.request.setParameter("key", keys);
        tester.start("/api/task/delete");

        assertThat(Datastore.getOrNull(foo.getKey()), equalTo(null));
        assertThat(Datastore.getOrNull(bar.getKey()), equalTo(null));
    }
}
