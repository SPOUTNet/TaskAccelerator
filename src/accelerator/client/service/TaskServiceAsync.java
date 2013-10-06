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
package accelerator.client.service;

import java.util.Date;
import java.util.List;

import accelerator.shared.model.Task;

import com.google.appengine.api.datastore.Key;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TaskServiceAsync {

    void getProjectTaskList(Key projectKey, AsyncCallback<List<Task>> callback);

    void createTask(Task task, AsyncCallback<Task> callback);

    void deleteTask(List<Key> keys, AsyncCallback<Void> callback);

    void updateTask(Task task, AsyncCallback<Task> callback);

    void getScheduledTaskList(AsyncCallback<List<Task>> callback);

    void getInboxTaskList(AsyncCallback<List<Task>> callback);

    void getTaggedTaskList(Key tagKey, AsyncCallback<List<Task>> callback);

    void untaggingTask(Key tagKey, AsyncCallback<Void> callback);

    void deleteTaskFromProject(Key projectKey, AsyncCallback<List<Key>> callback);

    void getTodayTaskList(Date today, AsyncCallback<List<Task>> callback);

    void getTask(Key key, AsyncCallback<Task> callback);

}
