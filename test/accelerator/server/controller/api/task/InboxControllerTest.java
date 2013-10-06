package accelerator.server.controller.api.task;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slim3.datastore.Datastore;
import org.slim3.tester.ControllerTestCase;

import accelerator.server.meta.TaskMeta;
import accelerator.shared.model.Task;

public class InboxControllerTest extends ControllerTestCase {
    private final TaskMeta meta = TaskMeta.get();

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
    public void 受信トレイにあるタスクを取得できるべき() throws Exception {
        Task t1 = new Task();
        t1.setName("foo");
        Datastore.put(t1);
        Task t2 = new Task();
        t2.setName("bar");
        Datastore.put(t2);

        tester.start("/api/task/inbox");
        String json = tester.response.getOutputAsString();
        Task[] result = meta.jsonToModels(json);
        assertThat(result.length, is(2));
    }
}
