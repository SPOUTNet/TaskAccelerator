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
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("service.s3gwt")
public interface TaskService extends RemoteService {
    /**
     * タスクを取得します。
     * 
     * @param key
     *            タスクのキー。
     * @return 該当するタスク。
     */
    Task getTask(Key key);

    /**
     * 指定したプロジェクトに属する未完了のタスクを取得します。
     * 
     * @param projectKey
     *            プロジェクトのキー。
     * @return プロジェクトに属するタスクのリスト。
     */
    List<Task> getProjectTaskList(Key projectKey);

    /**
     * 受信箱のタスクを取得します。
     * 
     * @return 受信箱のタスク。
     */
    List<Task> getInboxTaskList();

    /**
     * スケジュール済みのタスクを取得します。
     * 
     * @return スケジュール済みのタスク。
     */
    List<Task> getScheduledTaskList();

    /**
     * 指定したタグの付いたタスクを取得します。
     * 
     * @param tagKey
     *            タグのキー。
     * @return 指定したタグの付いたタスク。
     */
    List<Task> getTaggedTaskList(Key tagKey);

    /**
     * 今日実行するべきタスクを取得します。
     * 
     * @param today
     *            今日の日付。
     * @return 今日実行するべきタスク。
     * @throws Exception
     *             ログインしていないとき発生します。
     */
    List<Task> getTodayTaskList(Date today) throws Exception;

    /**
     * 新しいタスクを作成します。
     * 
     * @param task
     *            作成するタスク。
     * @return 作成したタスク。
     */
    Task createTask(Task task);

    /**
     * 指定したタスクを更新します。
     * 
     * @param task
     *            更新するタスク。
     */
    Task updateTask(Task task);

    /**
     * 指定したタスクを削除します。
     * 
     * @param keys
     *            タスクのキー。
     */
    void deleteTask(List<Key> keys);

    /**
     * 指定したタグのついたタスクからタグを削除します。
     * 
     * @param tagKey
     *            タグのキー。
     * @throws Exception
     *             タスクの保存に失敗したとき。
     */
    void untaggingTask(Key tagKey) throws Exception;

    /**
     * 指定したプロジェクトに属するタスクを削除します。
     * 
     * @param projectKey
     *            プロジェクトのキー。
     * @return 削除したタスクのキー。
     * @throws Exception
     *             タスクの削除に失敗したとき。
     */
    List<Key> deleteTaskFromProject(Key projectKey) throws Exception;
}
