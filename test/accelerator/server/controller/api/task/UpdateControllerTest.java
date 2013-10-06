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
import accelerator.shared.model.Project;
import accelerator.shared.model.Tag;
import accelerator.shared.model.Task;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class UpdateControllerTest extends ControllerTestCase {
    private final TaskMeta meta = TaskMeta.get();
    private Task target;

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

        target = new Task();
        target.setName("foo");
        Datastore.put(target);
    }

    @Test
    public void 指定したパラメータをもとにタスクを更新できるべき() throws Exception {
        tester.request.setMethod("POST");
        tester.param(meta.name, "bar");
        tester.param(meta.key, KeyFactory.keyToString(target.getKey()));
        tester.start("/api/task/update");

        Task actual = Datastore.get(meta, target.getKey());
        assertThat(actual.getName(), is("bar"));
    }

    @Test
    public void 更新したタスクをJSONで取得できるべき() throws Exception {
        tester.request.setMethod("POST");
        tester.param(meta.name, "bar");
        tester.param(meta.key, KeyFactory.keyToString(target.getKey()));
        tester.start("/api/task/update");

        String json = tester.response.getOutputAsString();
        Task task = meta.jsonToModel(json);
        assertThat(task.getName(), is("bar"));
    }

    @Test
    public void タスクにプロジェクトを設定できるべき() throws Exception {
        Project p = new Project();
        p.setName("hoge");
        Key projectKey = Datastore.put(p);

        tester.request.setMethod("POST");
        tester.param(meta.key, KeyFactory.keyToString(target.getKey()));
        tester.param(meta.project, KeyFactory.keyToString(projectKey));
        tester.start("/api/task/update");

        Task actual = Datastore.get(meta, target.getKey());
        assertThat(actual.getProject(), is(projectKey));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void 期日が設定されたタスクを作成できるべき() throws Exception {
        Date today = DateUtil.toDate("2011/08/04", "yyyy/MM/dd");

        tester.request.setMethod("POST");
        tester.param(meta.key, KeyFactory.keyToString(target.getKey()));
        tester.param(meta.dueDate, "2011/08/04");
        tester.start("/api/task/update");

        Task task = Datastore.get(meta, target.getKey());
        assertThat(task.getDueDate().getYear(), is(today.getYear()));
        assertThat(task.getDueDate().getMonth(), is(today.getMonth()));
        assertThat(task.getDueDate().getDay(), is(today.getDay()));
    }

    @Test
    public void タグが設定されたタスクを作成できるべき() throws Exception {
        Tag hogeTag = new Tag();
        hogeTag.setName("hoge");
        Key hogeKey = Datastore.put(hogeTag);

        Tag fugaTag = new Tag();
        fugaTag.setName("fuga");
        Key fugaKey = Datastore.put(fugaTag);

        String[] keyArray = new String[2];
        keyArray[0] = KeyFactory.keyToString(hogeKey);
        keyArray[1] = KeyFactory.keyToString(fugaKey);

        tester.request.setMethod("POST");
        tester.paramValues(meta.tags, keyArray);
        tester.param(meta.key, KeyFactory.keyToString(target.getKey()));
        tester.start("/api/task/update");

        Task task = Datastore.get(meta, target.getKey());
        assertThat(task.getTags().size(), is(2));
        assertThat(task.getTags().get(0), is(hogeKey));
        assertThat(task.getTags().get(1), is(fugaKey));
    }
}
