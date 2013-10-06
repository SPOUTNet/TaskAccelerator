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

public class IndexControllerTest extends ControllerTestCase {
    private final ProjectMeta meta = ProjectMeta.get();

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
    public void ProjectのリストをJSONで取得できる() throws Exception {
        Project p1 = new Project();
        p1.setName("foo");
        Datastore.put(p1);

        Project p2 = new Project();
        p2.setName("bar");
        Datastore.put(p2);

        tester.start("/api/project/");
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));

        String json = tester.response.getOutputAsString();
        Project[] projects = meta.jsonToModels(json);
        assertThat(projects.length, is(2));
    }
}
