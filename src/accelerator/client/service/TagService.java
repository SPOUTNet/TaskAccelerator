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

import accelerator.shared.model.Tag;

import com.google.appengine.api.datastore.Key;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("service.s3gwt")
public interface TagService extends RemoteService {
    /**
     * タグを取得します。
     * 
     * @param key
     *            タグのキー。
     * @return 指定したキーに該当するタグ。
     */
    Tag getTag(Key key);

    /**
     * ユーザーが作成したタグのリストを取得します。
     * 
     * @return タグのリスト。
     * @throws Exception
     *             ログインしていないとき発生します。
     */
    List<Tag> getTagList() throws Exception;

    /**
     * 新しいタグを作成します。
     * 
     * @param input
     *            ユーザーの入力。
     * @return 作成されたタグ。
     * @throws Exception
     *             ログインしていないとき発生します。
     */
    Tag createTag(Tag input) throws Exception;

    /**
     * タグの名前を変更します。
     * 
     * @param input
     *            ユーザーの入力。
     * @return 変更されたタグ。
     * @throws Exception
     *             ログインしていないとき発生します。
     */
    Tag updateTag(Tag input) throws Exception;

    /**
     * タグを削除します。
     * 
     * @param key
     *            削除するタグのキー。
     * @return 削除されたタグのキー。
     * @throws Exception
     *             ログインしていないとき発生します。
     */
    Key deleteTag(Key key) throws Exception;
}
