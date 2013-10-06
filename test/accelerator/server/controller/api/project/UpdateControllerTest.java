package accelerator.server.controller.api.project;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slim3.datastore.Datastore;
import org.slim3.tester.ControllerTestCase;

import accelerator.server.meta.ProjectMeta;
import accelerator.server.util.HttpStatus;
import accelerator.shared.model.Project;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class UpdateControllerTest extends ControllerTestCase {
    private static final ProjectMeta meta = ProjectMeta.get();
    private Key projectKey;

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

        // テストデータ追加
        Project project = new Project();
        project.setName("foo");
        projectKey = Datastore.put(project);
    }

    @Test
    public void Projectを更新できる() throws Exception {
        tester.param(meta.name, "bar");
        tester.param(meta.key, KeyFactory.keyToString(projectKey));
        tester.request.setMethod("POST");
        tester.start("/api/project/update");

        UpdateController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));

        Project actual = Datastore.get(meta, projectKey);
        assertThat(actual.getName(), is("bar"));
    }

    @Test
    public void 作成したProjectをJSONで取得できる() throws Exception {
        tester.param(meta.name, "bar");
        tester.param(meta.key, KeyFactory.keyToString(projectKey));
        tester.request.setMethod("POST");
        tester.start("/api/project/update");

        UpdateController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));

        String json = tester.response.getOutputAsString();
        Project project = meta.jsonToModel(json);
        assertThat(project.getName(), is("bar"));
    }

    @Test
    public void POSTメソッド以外だとMethodNotAllowedを返す() throws Exception {
        tester.param(meta.key, KeyFactory.keyToString(projectKey));
        tester.param(meta.name, "bar");
        tester.request.setMethod("GET");
        tester.start("/api/project/update");
        assertThat(tester.response.getStatus(), is(HttpStatus.MethodNotAllowed));
    }
}
