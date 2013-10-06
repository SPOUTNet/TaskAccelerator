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
package accelerator.server.controller.queue.task;

import org.slim3.datastore.Datastore;
import org.slim3.tester.ControllerTestCase;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.KeyFactory;

import accelerator.server.util.HttpStatus;
import accelerator.shared.model.Project;
import accelerator.shared.model.Task;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class DeleteControllerTest extends ControllerTestCase {
    private Project project;
    
    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();

        project = new Project();
        project.setName("foo");
        Datastore.put(project);
        
        Task t1 = new Task();
        t1.setName("hoge");
        t1.setProject(project.getKey());
        Datastore.put(t1);
    }
    
    @Test
    public void 指定したプロジェクトに属するタスクを削除できるべき() throws Exception {
        tester.request.setMethod("POST");
        tester.request.setParameter("projectKey", KeyFactory.keyToString(project.getKey()));
        tester.start("/queue/task/delete");

        DeleteController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.response.getStatus(), is(HttpStatus.OK));
        assertThat(Datastore.query(Task.class).count(), is(0));
    }
    
    @Test
    public void POSTメソッドじゃないときMethodNotAllowedを返すべき() throws Exception {       
        tester.start("/queue/task/delete");
        
        DeleteController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.response.getStatus(), is(HttpStatus.MethodNotAllowed));
        assertThat(Datastore.query(Task.class).count(), is(1));
    }

    @Test
    public void projectKeyが指定されていないときBadRequestを返すべき() throws Exception {
        tester.request.setMethod("POST");
        tester.start("/queue/task/delete");
        
        DeleteController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.response.getStatus(), is(HttpStatus.BadRequest));
        assertThat(Datastore.query(Task.class).count(), is(1));
    }
}
