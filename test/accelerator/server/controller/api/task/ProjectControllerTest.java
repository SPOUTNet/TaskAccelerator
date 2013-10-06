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
import accelerator.shared.model.Project;
import accelerator.shared.model.Task;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class ProjectControllerTest extends ControllerTestCase {
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
    public void 指定したプロジェクトに属するタスクをJSONで取得できるべき() throws Exception {
        Project foo = new Project();
        foo.setName("foo");
        Key fooKey = Datastore.put(foo);

        Task hoge = new Task();
        hoge.setName("hoge");
        hoge.setProject(fooKey);
        Datastore.put(hoge);

        Task fuga = new Task();
        fuga.setName("fuga");
        fuga.setProject(fooKey);
        Datastore.put(fuga);

        tester.request.setParameter(
            "projectKey",
            KeyFactory.keyToString(foo.getKey()));
        tester.start("/api/task/project");

        String json = tester.response.getOutputAsString();
        Task[] result = meta.jsonToModels(json);
        assertThat(result.length, is(2));
    }
}
