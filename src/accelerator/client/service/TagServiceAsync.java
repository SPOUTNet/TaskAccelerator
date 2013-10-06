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
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TagServiceAsync {

    void createTag(Tag input, AsyncCallback<Tag> callback);

    void updateTag(Tag input, AsyncCallback<Tag> callback);

    void deleteTag(Key key, AsyncCallback<Key> callback);

    void getTagList(AsyncCallback<List<Tag>> callback);

    void getTag(Key key, AsyncCallback<Tag> callback);

}
