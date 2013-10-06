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

import java.util.List;

import org.slim3.datastore.Datastore;
import org.slim3.tester.ControllerTestCase;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.KeyFactory;

import accelerator.server.meta.TaskMeta;
import accelerator.server.util.HttpStatus;
import accelerator.shared.model.Tag;
import accelerator.shared.model.Task;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class UntaggingControllerTest extends ControllerTestCase {
    private static final TaskMeta meta = TaskMeta.get();
    private Tag tag;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();

        tag = new Tag();
        tag.setName("foo");
        Datastore.put(tag);

        Task t1 = new Task();
        t1.setName("hoge");
        t1.getTags().add(tag.getKey());
        Datastore.put(t1);
    }

    @Test
    public void 指定したタグの付いたタスクからタグを削除するべき() throws Exception {
        tester.request.setParameter(
            "tagKey",
            KeyFactory.keyToString(tag.getKey()));
        tester.request.setMethod("POST");
        tester.start("/queue/task/untagging");

        UntaggingController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.response.getStatus(), is(HttpStatus.OK));

        List<Task> results = Datastore.query(meta).asList();
        assertThat(results.size(), is(1));
        assertThat(results.get(0).getTags().isEmpty(), is(true));
    }

    @Test
    public void POSTメソッドじゃないときMethodNotAllowedを返すべき() throws Exception {
        tester.request.setParameter(
            "tagKey",
            KeyFactory.keyToString(tag.getKey()));
        tester.start("/queue/task/untagging");

        UntaggingController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.response.getStatus(), is(HttpStatus.MethodNotAllowed));
    }

    @Test
    public void tagKeyを指定していないときBadRequestを返すべき() throws Exception {
        tester.request.setMethod("POST");
        tester.start("/queue/task/untagging");

        UntaggingController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.response.getStatus(), is(HttpStatus.BadRequest));
    }
}
