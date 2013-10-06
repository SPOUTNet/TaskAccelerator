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

import java.util.List;

import accelerator.shared.model.Project;

import com.google.appengine.api.datastore.Key;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("service.s3gwt")
public interface ProjectService extends RemoteService {
    /**
     * プロジェクトを取得します。
     * 
     * @param key
     *            プロジェクトのキー。
     * @return 指定したキーに該当するプロジェクト。
     */
    Project getProject(Key key);

    /**
     * プロジェクトのリストを取得します。
     * 
     * @return プロジェクトのリスト。
     * @throws Exception
     *             ログインしていないとき発生します。
     */
    List<Project> getProjectList() throws Exception;

    /**
     * プロジェクトを作成します。
     * 
     * @param input
     *            ユーザーの入力。
     * @return 作成したプロジェクト。
     * @throws Exception
     *             ログインしていないとき発生します。
     */
    Project createProject(Project input) throws Exception;

    /**
     * プロジェクト名を変更します。
     * 
     * @param input
     *            ユーザーの入力。
     * @return 変更されたプロジェクト。
     * @throws Exception
     *             ログインしていないとき発生します。
     */
    Project updateProject(Project input) throws Exception;

    /**
     * プロジェクトを削除します。
     * 
     * @param key
     *            削除するプロジェクトのキー。
     * @return 削除されたプロジェクトのキー。
     * @throws Exception
     *             ログインしていないとき発生します。
     */
    Key deleteProject(Key key) throws Exception;
}
