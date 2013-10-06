package accelerator.server.controller.api.task;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slim3.datastore.Datastore;
import org.slim3.tester.ControllerTestCase;
import org.slim3.util.DateUtil;

import accelerator.server.meta.TaskMeta;
import accelerator.shared.model.Task;

public class TodayControllerTest extends ControllerTestCase {
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
    public void 今日やるタスクを取得できるべき() throws Exception {
        Date today = DateUtil.toDate("2011/08/03", "yyyy/MM/dd");
        Date tomorrow = DateUtil.toDate("2011/08/04", "yyyy/MM/dd");

        Task t1 = new Task();
        t1.setName("foo");
        t1.setDueDate(today);
        Datastore.put(t1);

        Task t2 = new Task();
        t2.setName("bar");
        t2.setDueDate(today);
        Datastore.put(t2);

        Task t3 = new Task();
        t3.setName("hoge");
        t3.setDueDate(tomorrow);
        Datastore.put(t3);

        tester.param("today", "2011/08/03");
        tester.start("/api/task/today");

        String json = tester.response.getOutputAsString();
        Task[] result = meta.jsonToModels(json);
        assertThat(result.length, is(2));
    }
}
