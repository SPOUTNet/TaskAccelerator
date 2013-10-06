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
import accelerator.shared.model.Project;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class DeleteControllerTest extends ControllerTestCase {
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
    public void Projectを削除できる() throws Exception {
        tester.request.setMethod("POST");
        tester.param(meta.key, KeyFactory.keyToString(projectKey));
        tester.start("/api/project/delete");

        DeleteController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));

        assertThat(Datastore.query(meta).count(), is(0));
    }
}
