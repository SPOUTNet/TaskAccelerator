/**
 * Task Accelerator - Full ajax task management application run on Google App Engine -
 * Copyright (C) 2011 tnakamura
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation;
 * either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package accelerator.server.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slim3.datastore.Datastore;
import org.slim3.tester.ServletTestCase;

import accelerator.server.meta.ProjectMeta;
import accelerator.shared.model.Project;

import com.google.appengine.api.datastore.Key;

public class ProjectServiceImplTest extends ServletTestCase {
    private final static ProjectMeta meta = ProjectMeta.get();
    private final ProjectServiceImpl service = new ProjectServiceImpl();

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
    public void testCreateProject() throws Exception {
        Project input = new Project();
        input.setName("foo");
        Project project = service.createProject(input);

        assertThat(project.getKey(), is(notNullValue()));
        assertThat(project.getName(), is("foo"));
    }

    @Test
    public void testUpdateProject() throws Exception {
        Project project = new Project();
        project.setName("foo");
        Datastore.put(project);

        project.setName("bar");
        Project actual = service.updateProject(project);
        assertThat(actual.getName(), is("bar"));
    }

    @Test
    public void testGetProjectList() throws Exception {
        Project foo = new Project();
        foo.setName("foo");
        Datastore.put(foo);

        List<Project> actual = service.getProjectList();
        assertThat(actual.size(), is(1));
        assertThat(actual.get(0).getName(), is("foo"));
    }

    @Test
    public void testDeleteProject() throws Exception {
        Project foo = new Project();
        foo.setName("foo");
        Key key = Datastore.put(foo);
        assertThat(Datastore.query(meta).count(), is(1));

        service.deleteProject(key);
        assertThat(Datastore.query(meta).count(), is(0));
    }
}
