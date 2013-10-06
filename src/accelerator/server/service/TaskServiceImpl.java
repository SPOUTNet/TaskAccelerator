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

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.GlobalTransaction;

import accelerator.client.service.TaskService;
import accelerator.server.meta.TaskMeta;
import accelerator.server.util.Requires;
import accelerator.server.util.ServiceUtil;
import accelerator.shared.model.Task;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class TaskServiceImpl implements TaskService {
    private static final TaskMeta meta = TaskMeta.get();
    private static final Logger logger = Logger.getLogger(TaskServiceImpl.class
        .getName());

    /**
     * {@inheritDoc}
     */
    public List<Task> getProjectTaskList(Key projectKey) {
        Requires.notNull(projectKey, "projectKey");

        UserService users = UserServiceFactory.getUserService();
        User user = users.getCurrentUser();
        List<Task> results =
            Datastore
                .query(meta)
                .filter(meta.user.equal(user), meta.project.equal(projectKey))
                .sort(meta.project.asc, meta.dueDate.asc, meta.createdDate.asc)
                .asList();

        return results;
    }

    /**
     * {@inheritDoc}
     */
    public Task createTask(Task task) throws IllegalArgumentException {
        Requires.notNull(task, "task");
        Requires.isNull(task.getKey(), "task.key");

        task.setCompleted(false);
        Transaction tx = Datastore.beginTransaction();
        Datastore.put(tx, task);
        tx.commit();
        return task;
    }

    /**
     * {@inheritDoc}
     */
    public void deleteTask(List<Key> keys) {
        Requires.notNull(keys, "keys");

        GlobalTransaction gtx = Datastore.beginGlobalTransaction();
        gtx.delete(keys);
        gtx.commit();
    }

    /**
     * {@inheritDoc}
     */
    public Task updateTask(Task task) {
        Requires.notNull(task, "task");
        Requires.notNull(task.getKey(), "task.key");

        Transaction tx = Datastore.beginTransaction();
        Datastore.put(tx, task);
        tx.commit();
        return task;
    }

    /**
     * {@inheritDoc}
     */
    public List<Task> getScheduledTaskList() {
        UserService users = UserServiceFactory.getUserService();
        User user = users.getCurrentUser();
        List<Task> taskList =
            Datastore
                .query(meta)
                .filter(meta.user.equal(user), meta.dueDate.isNotNull())
                .sort(meta.dueDate.asc, meta.project.asc, meta.createdDate.asc)
                .asList();
        return taskList;
    }

    /**
     * {@inheritDoc}
     */
    public List<Task> getInboxTaskList() {
        UserService users = UserServiceFactory.getUserService();
        User user = users.getCurrentUser();
        List<Task> taskList =
            Datastore
                .query(meta)
                .filter(meta.user.equal(user), meta.project.equal(null))
                .sort(meta.dueDate.asc, meta.createdDate.asc)
                .asList();
        return taskList;
    }

    /**
     * {@inheritDoc}
     */
    public List<Task> getTaggedTaskList(Key tagKey) {
        Requires.notNull(tagKey, "tagKey");

        UserService users = UserServiceFactory.getUserService();
        User user = users.getCurrentUser();
        List<Task> taskList =
            Datastore
                .query(meta)
                .filter(meta.user.equal(user), meta.tags.equal(tagKey))
                .sort(meta.project.asc, meta.dueDate.asc, meta.createdDate.asc)
                .asList();

        return taskList;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws Exception
     */
    public void untaggingTask(Key tagKey) throws Exception {
        Requires.notNull(tagKey, "tagKey");

        List<Task> tasks =
            Datastore.query(meta).filter(meta.tags.equal(tagKey)).asList();
        for (Task t : tasks) {
            t.getTags().remove(tagKey);
        }

        try {
            GlobalTransaction gtx = Datastore.beginGlobalTransaction();
            gtx.put(tasks);
            gtx.commit();
        } catch (Exception ex) {
            logger.warning(ex.toString());
            throw ex;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @throws Exception
     */
    public List<Key> deleteTaskFromProject(Key projectKey) throws Exception {
        Requires.notNull(projectKey, "projectKey");

        List<Key> keys =
            Datastore
                .query(meta)
                .filter(meta.project.equal(projectKey))
                .asKeyList();

        try {
            GlobalTransaction gtx = Datastore.beginGlobalTransaction();
            gtx.delete(keys);
            gtx.commit();
            return keys;
        } catch (Exception ex) {
            logger.warning(ex.toString());
            throw ex;
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<Task> getTodayTaskList(Date today) throws Exception {
        Requires.notNull(today, "today");
        User user = ServiceUtil.getUserOrException();

        // 今日の 23時59分59秒をもとめる
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        Date deadline = cal.getTime();

        return Datastore
            .query(meta)
            .filter(
                meta.user.equal(user),
                meta.dueDate.isNotNull(),
                meta.dueDate.lessThanOrEqual(deadline))
            .sort(meta.dueDate.asc, meta.project.asc, meta.createdDate.asc)
            .asList();
    }

    /**
     * {@inheritDoc}
     */
    public Task getTask(Key key) {
        return Datastore.get(meta, key);
    }
}
