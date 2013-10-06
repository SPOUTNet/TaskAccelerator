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
package accelerator.shared.util;

import java.util.List;


import accelerator.shared.model.Tag;
import accelerator.shared.model.Task;

import com.google.appengine.api.datastore.Key;

public class ModelUtil {
    /**
     * コンストラクタ。
     */
    private ModelUtil() {

    }

    public static List<Key> tagsToKeyList(Iterable<Tag> tags) {
        List<Key> keyList = CollectionUtil.createArrayList();
        for (Tag tag : tags) {
            keyList.add(tag.getKey());
        }
        return keyList;
    }

    public static List<Key> tasksToKeyList(Iterable<Task> tasks) {
        List<Key> keyList = CollectionUtil.createArrayList();
        for (Task task : tasks) {
            keyList.add(task.getKey());
        }
        return keyList;
    }
}
