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

import accelerator.client.service.TagService;
import accelerator.server.meta.TagMeta;
import accelerator.server.util.Requires;
import accelerator.server.util.ServiceUtil;
import accelerator.shared.model.Tag;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import com.google.appengine.api.users.User;

public class TagServiceImpl implements TagService {
    private static final TagMeta meta = TagMeta.get();
    private static final Logger logger = Logger.getLogger(TagServiceImpl.class
        .getName());

    /**
     * {@inheritDoc}
     */
    public List<Tag> getTagList() throws Exception {
        User user = ServiceUtil.getUserOrException();
        List<Tag> result =
            Datastore
                .query(meta)
                .filter(meta.user.equal(user))
                .sort(meta.name.asc)
                .asList();
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public Tag createTag(Tag input) throws Exception {
        ServiceUtil.getUserOrException();
        Requires.notNull(input, "input");
        Requires.isNull(input.getKey(), "input.key");

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
    public Tag updateTag(Tag input) throws Exception {
        ServiceUtil.getUserOrException();
        Requires.notNull(input, "input");
        Requires.notNull(input.getKey(), "input.key");

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
    public Key deleteTag(Key key) throws Exception {
        ServiceUtil.getUserOrException();
        Requires.notNull(key, "key");

        try {
            Transaction tx = Datastore.beginTransaction();
            Datastore.delete(tx, key);
            tx.commit();
        } catch (Exception ex) {
            logger.warning(ex.toString());
            throw ex;
        }

        Queue queue = QueueFactory.getDefaultQueue();
        TaskOptions options =
            TaskOptions.Builder
                .withUrl("/queue/task/untagging")
                .param("tagKey", KeyFactory.keyToString(key))
                .method(Method.POST);
        queue.add(options);

        return key;
    }

    /**
     * {@inheritDoc}
     */
    public Tag getTag(Key key) {
        return Datastore.get(meta, key);
    }
}
