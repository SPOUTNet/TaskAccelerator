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
package accelerator.client;

import accelerator.client.service.LogServiceAsync;
import accelerator.client.service.LoginServiceAsync;
import accelerator.client.service.ProjectServiceAsync;
import accelerator.client.service.TagServiceAsync;
import accelerator.client.service.TaskServiceAsync;
import accelerator.client.view.MainMenuView;
import accelerator.client.view.Shell;
import accelerator.client.view.TaskListView;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;

public interface ClientFactory {
    
    /**
     * EventBus を取得します。
     * @return EventBus インスタンス。
     */
    EventBus getEventBus();
    
    /**
     * PlaceController を取得します。
     * @return PlaceController インスタンス。
     */
    PlaceController getPlaceController();
    
    /**
     * LoginService を取得します。
     * @return LoginService を実装するインスタンス。
     */
    LoginServiceAsync getLoginService();
    
    /**
     * LogService を取得します。
     * @return LogService を実装するインスタンス。
     */
    LogServiceAsync getLogService();
    
    /**
     * TaskService を取得します。
     * @return TaskService を実装するインスタンス。
     */
    TaskServiceAsync getTaskService();
    
    /**
     * ProjectService を取得します。
     * @return ProjectService を実装するインスタンス。
     */
    ProjectServiceAsync getProjectService();
    
    /**
     * TagService を取得します。
     * @return TagService を実装するインスタンス。
     */
    TagServiceAsync getTagService();
    
    /**
     * シェルを取得します。
     * @return シェル。
     */
    Shell getShell();
    
    /**
     * タスクを表示するビューを取得します。
     * @return タスクを表示するビュー。
     */
    TaskListView getTaskListView();
    
    /**
     * メインメニューを取得します。
     * @return メインメニュー。
     */
    MainMenuView getMainMenuView();
}
