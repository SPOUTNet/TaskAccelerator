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

import java.util.List;
import java.util.logging.Logger;

import org.slim3.datastore.Datastore;

import accelerator.client.service.ProjectService;
import accelerator.server.meta.ProjectMeta;
import accelerator.server.util.Requires;
import accelerator.server.util.ServiceUtil;
import accelerator.shared.model.Project;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.users.User;

public class ProjectServiceImpl implements ProjectService {
    private static final ProjectMeta meta = ProjectMeta.get();
    private static final Logger logger = Logger
        .getLogger(ProjectServiceImpl.class.getName());

    /**
     * {@inheritDoc}
     */
    public List<Project> getProjectList() throws Exception {
        User user = ServiceUtil.getUserOrException();
        List<Project> results =
            Datastore
                .query(meta)
                .filter(meta.user.equal(user))
                .sort(meta.name.asc)
                .asList();
        return results;
    }

    /**
     * {@inheritDoc}
     */
    public Project createProject(Project input) throws Exception {
        ServiceUtil.getUserOrException();
        Requires.notNull(input, "project");
        Requires.isNull(input.getKey(), "project.key");

        try {
            Transaction tx = Datastore.beginTransaction();
            Datastore.put(tx, input);
            tx.commit();
            return input;
        } catch (Exception ex) {
            logger.warning(ex.toString());
            throw ex;
        }
    }

    /**
     * {@inheritDoc}
     */
    public Project updateProject(Project input) throws Exception {
        ServiceUtil.getUserOrException();
        Requires.notNull(input, "input");
        Requires.notNull(input.getKey(), "input.key");
        Requires.notNullOrEmpty(input.getName(), "input.name");

        try {
            Transaction tx = Datastore.beginTransaction();
            Datastore.put(tx, input);
            tx.commit();
            return input;
        } catch (Exception ex) {
            logger.warning(ex.toString());
            throw ex;
        }
    }

    /**
     * {@inheritDoc}
     */
    public Key deleteProject(Key key) throws Exception {
        ServiceUtil.getUserOrException();
        Requires.notNull(key, "key");

        // プロジェクト削除
        try {
            Transaction tx = Datastore.beginTransaction();
            Datastore.delete(tx, key);
            tx.commit();
        } catch (Exception ex) {
            logger.warning(ex.toString());
            throw ex;
        }

        // タスク削除
        Queue queue = QueueFactory.getDefaultQueue();
        TaskOptions options =
            TaskOptions.Builder.withUrl("/queue/task/delete").param(
                "projectKey",
                KeyFactory.keyToString(key));
        queue.add(options);

        return key;
    }

    /**
     * {@inheritDoc}
     */
    public Project getProject(Key key) {
        return Datastore.get(meta, key);
    }
}
