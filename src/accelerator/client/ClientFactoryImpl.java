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

import accelerator.client.service.LogService;
import accelerator.client.service.LogServiceAsync;
import accelerator.client.service.LoginService;
import accelerator.client.service.LoginServiceAsync;
import accelerator.client.service.ProjectService;
import accelerator.client.service.ProjectServiceAsync;
import accelerator.client.service.TagService;
import accelerator.client.service.TagServiceAsync;
import accelerator.client.service.TaskService;
import accelerator.client.service.TaskServiceAsync;
import accelerator.client.view.MainMenuView;
import accelerator.client.view.Shell;
import accelerator.client.view.TaskListView;
import accelerator.client.view.desktop.DesktopMainMenuView;
import accelerator.client.view.desktop.DesktopShell;
import accelerator.client.view.desktop.DesktopTaskListView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;


public class ClientFactoryImpl implements ClientFactory {
    private final EventBus eventBus = new SimpleEventBus();
    private final PlaceController placeController = new PlaceController(
        eventBus);

    /**
     * 認証サービス。
     */
    private final LoginServiceAsync loginService = GWT
        .create(LoginService.class);

    /**
     * タスクを操作するサービス。
     */
    private final TaskServiceAsync taskService = GWT.create(TaskService.class);

    /**
     * プロジェクトを操作するサービス。
     */
    private final ProjectServiceAsync projectService = GWT
        .create(ProjectService.class);

    /**
     * タグを操作するサービス。
     */
    private final TagServiceAsync tagService = GWT.create(TagService.class);

    /**
     * ログを出力するサービス。
     */
    private final LogServiceAsync logService = GWT.create(LogService.class);

    private Shell mainView;

    /**
     * タスク一覧を表示するビュー。
     */
    private TaskListView taskListView;

    /**
     * メインメニューを表示するビュー。
     */
    private MainMenuView mainMenuView;

    /**
     * {@inheritDoc}
     */
    public final LoginServiceAsync getLoginService() {
        return loginService;
    }

    /**
     * {@inheritDoc}
     */
    public final TaskServiceAsync getTaskService() {
        return taskService;
    }

    /**
     * {@inheritDoc}
     */
    public final ProjectServiceAsync getProjectService() {
        return projectService;
    }

    /**
     * {@inheritDoc}
     */
    public final TagServiceAsync getTagService() {
        return tagService;
    }

    /**
     * {@inheritDoc}
     */
    public final LogServiceAsync getLogService() {
        return logService;
    }

    /**
     * {@inheritDoc}
     */
    public final Shell getShell() {
        if (mainView == null) {
            mainView = createShell();
        }
        return mainView;
    }

    /**
     * シェルを作成します。
     * 
     * @return 作成したシェル。
     */
    protected Shell createShell() {
        return new DesktopShell(this);
    }

    /**
     * {@inheritDoc}
     */
    public final EventBus getEventBus() {
        return eventBus;
    }

    /**
     * {@inheritDoc}
     */
    public final PlaceController getPlaceController() {
        return placeController;
    }

    /**
     * {@inheritDoc}
     */
    public final TaskListView getTaskListView() {
        if (taskListView == null) {
            taskListView = createTaskListView();
        }
        return taskListView;
    }

    protected TaskListView createTaskListView() {
        return new DesktopTaskListView();
    }

    /**
     * {@inheritDoc}
     */
    public final MainMenuView getMainMenuView() {
        if (mainMenuView == null) {
            mainMenuView = createMainMenuView();
        }
        return mainMenuView;
    }

    protected MainMenuView createMainMenuView() {
        return new DesktopMainMenuView();
    }
}
