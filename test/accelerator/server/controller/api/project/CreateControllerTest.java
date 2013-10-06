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

public class CreateControllerTest extends ControllerTestCase {
    private static final ProjectMeta meta = ProjectMeta.get();

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
    public void Projectを作成できる() throws Exception {
        tester.param(meta.name, "foo");
        tester.request.setMethod("POST");
        tester.start("/api/project/create");

        CreateController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));

        assertThat(Datastore.query(meta).count(), is(1));
    }

    @Test
    public void 作成したProjectをJSONで取得できる() throws Exception {
        tester.param(meta.name, "foo");
        tester.request.setMethod("POST");
        tester.start("/api/project/create");

        CreateController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));

        String json = tester.response.getOutputAsString();
        Project project = meta.jsonToModel(json);
        assertThat(project.getName(), is("foo"));
    }

    @Test
    public void POSTメソッド以外だとMethodNotAllowedを返す() throws Exception {
        tester.param(meta.name, "foo");
        tester.request.setMethod("GET");
        tester.start("/api/project/create");
        assertThat(tester.response.getStatus(), is(HttpStatus.MethodNotAllowed));
    }
}
