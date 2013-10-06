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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slim3.datastore.Datastore;
import org.slim3.tester.ServletTestCase;

import accelerator.server.meta.TaskMeta;
import accelerator.shared.model.Project;
import accelerator.shared.model.Tag;
import accelerator.shared.model.Task;

import com.google.appengine.api.datastore.Key;

public class TaskServiceImplTest extends ServletTestCase {
    private final TaskMeta meta = TaskMeta.get();
    private final TaskServiceImpl service = new TaskServiceImpl();

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
    public void createTaskでタスクを作成できる() throws Exception {
        Task task = new Task();
        task.setName("foo");
        Task result = service.createTask(task);
        assertThat(result.getKey(), is(notNullValue()));
        assertThat(result.getName(), is("foo"));
        assertThat(Datastore.query(meta).count(), is(1));
    }

    @Test
    public void updateTaskで指定したタスクを更新できる() throws Exception {
        Task task = new Task();
        task.setName("bar");
        Key key = Datastore.put(task);

        Task target = Datastore.get(meta, key);
        target.setName("fuga");
        Task result = service.updateTask(target);
        assertThat(result.getName(), is("fuga"));

        Task actual = Datastore.get(meta, key);
        assertThat(actual.getName(), is("fuga"));
    }

    @Test
    public void deleteTaskで指定したタスクを削除できる() throws Exception {
        Task fooTask = new Task();
        fooTask.setName("foo");
        Key fooKey = Datastore.put(fooTask);
        assertThat(Datastore.query(meta).count(), is(1));

        List<Key> keyList = new ArrayList<Key>();
        keyList.add(fooKey);
        service.deleteTask(keyList);
        assertThat(Datastore.query(meta).count(), is(0));
    }

    @Test
    public void getInboxTaskListでプロジェクトに属していないタスクを取得できる() throws Exception {
        Task fooTask = new Task();
        fooTask.setName("foo");
        Datastore.put(fooTask);

        Project hogeProject = new Project();
        hogeProject.setName("hoge");
        Key hogeKey = Datastore.put(hogeProject);
        Task barTask = new Task();
        barTask.setName("bar");
        barTask.setProject(hogeKey);
        Datastore.put(barTask);

        List<Task> taskList = service.getInboxTaskList();
        assertThat(taskList.size(), is(1));
        assertThat(taskList.get(0).getName(), is("foo"));
    }

    @Test
    public void getScheduleTaskListで期日が設定されているタスクを取得できる() throws Exception {
        Task fooTask = new Task();
        fooTask.setName("foo");
        fooTask.setDueDate(new Date());
        Datastore.put(fooTask);

        Task barTask = new Task();
        barTask.setName("bar");
        Datastore.put(barTask);

        List<Task> taskList = service.getScheduledTaskList();
        assertThat(taskList.size(), is(1));
        assertThat(taskList.get(0).getName(), is("foo"));
    }

    @Test
    public void getProjectTaskListで指定したプロジェクトに属するタスクを取得できる() throws Exception {
        Task fooTask = new Task();
        fooTask.setName("foo");
        Datastore.put(fooTask);

        Project hogeProject = new Project();
        hogeProject.setName("hoge");
        Key hogeKey = Datastore.put(hogeProject);

        Task barTask = new Task();
        barTask.setName("bar");
        barTask.setProject(hogeKey);
        Datastore.put(barTask);

        List<Task> taskList = service.getProjectTaskList(hogeKey);
        assertThat(taskList.size(), is(1));
        assertThat(taskList.get(0).getName(), is("bar"));
    }

    @Test
    public void getTaggedTaskListで指定したタグが付いたタスクを取得できる() throws Exception {
        Task fooTask = new Task();
        fooTask.setName("foo");
        Datastore.put(fooTask);

        Tag hogeTag = new Tag();
        hogeTag.setName("hoge");
        Key hogeKey = Datastore.put(hogeTag);

        Task barTask = new Task();
        barTask.setName("bar");
        barTask.getTags().add(hogeKey);
        Datastore.put(barTask);

        List<Task> taskList = service.getTaggedTaskList(hogeKey);
        assertThat(taskList.size(), is(1));
        assertThat(taskList.get(0).getName(), is("bar"));
    }

    @Test
    public void untaggingTaskで指定したタグが付いたタスクからタグを除去できる() throws Exception {
        Tag fooTag = new Tag();
        fooTag.setName("foo");
        Datastore.put(fooTag);

        Task hogeTask = new Task();
        hogeTask.setName("hoge");
        hogeTask.getTags().add(fooTag.getKey());
        Datastore.put(hogeTask);
        assertThat(Datastore.query(meta).count(), equalTo(1));

        service.untaggingTask(fooTag.getKey());
        Task actual = Datastore.get(meta, hogeTask.getKey());
        assertThat(actual.getTags().isEmpty(), is(true));
    }

    @Test
    public void deleteTaskFromProjectで指定したプロジェクトに属するタスクを削除できる()
            throws Exception {
        Project fooProject = new Project();
        fooProject.setName("foo");
        Datastore.put(fooProject);

        Task hogeTask = new Task();
        hogeTask.setName("hoge");
        hogeTask.setProject(fooProject.getKey());
        Datastore.put(hogeTask);

        Task fugaTask = new Task();
        fugaTask.setName("hoge");
        fugaTask.setProject(fooProject.getKey());
        Datastore.put(fugaTask);

        assertThat(Datastore.query(meta).count(), equalTo(2));

        service.deleteTaskFromProject(fooProject.getKey());
        assertThat(Datastore.query(meta).count(), equalTo(0));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void getTodayTaskListで今日やるべきタスクを取得できる() throws Exception {
        Task t1 = new Task();
        t1.setName("foo");
        t1.setDueDate(new Date(2011, 7, 27, 23, 59, 59));
        Datastore.put(t1);

        Task t2 = new Task();
        t2.setName("bar");
        t2.setDueDate(new Date(2011, 7, 28, 0, 0, 0));
        Datastore.put(t2);

        assertThat(Datastore.query(meta).count(), is(2));

        Date today = new Date(2011, 7, 27);
        List<Task> taskList = service.getTodayTaskList(today);
        assertThat(taskList.size(), is(1));
    }
}
