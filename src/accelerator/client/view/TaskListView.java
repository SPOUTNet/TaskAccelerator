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
package accelerator.client.view;

import java.util.Collection;
import java.util.List;

import accelerator.shared.model.Project;
import accelerator.shared.model.Tag;
import accelerator.shared.model.Task;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;


/**
 * タスクを表示するビューのインタフェース。
 */
public interface TaskListView extends IsWidget {
    public static interface Presenter {
        void loadTaskList();
        void createTask(Task task);
        void updateTask(Task task);
        void deleteTask(Collection<Task> keys);
        void goTo(Place place);
    }
    
    void setPresenter(Presenter presenter);
    void setTaskList(List<Task> tasks);
    void setProjectList(List<Project> projects);
    void setTagList(List<Tag> tags);
}
