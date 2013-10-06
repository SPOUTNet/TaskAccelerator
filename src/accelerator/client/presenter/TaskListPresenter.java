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
package accelerator.client.presenter;

import java.util.Collection;
import java.util.List;


import accelerator.client.ClientFactory;
import accelerator.client.event.PrepareTaskListEvent;
import accelerator.client.event.ShowStatusEvent;
import accelerator.client.event.UpdateProjectListEvent;
import accelerator.client.event.UpdateTagListEvent;
import accelerator.client.service.TaskServiceAsync;
import accelerator.client.view.TaskListView;
import accelerator.client.view.TaskListView.Presenter;
import accelerator.shared.model.Task;
import accelerator.shared.util.CollectionUtil;
import accelerator.shared.util.ModelUtil;

import com.google.appengine.api.datastore.Key;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

/**
 * タスクの一覧を表示するビューのプレゼンター。 
 */
public abstract class TaskListPresenter extends AbstractActivity implements
        Presenter {
    protected final ClientFactory clientFactory;
    protected final TaskServiceAsync service;
    private final List<Task> taskList = CollectionUtil.createArrayList();
    private TaskListView view;

    /**
     * コンストラクタ。
     * 
     * @param clientFactory
     *            クライアントファクトリ。
     */
    protected TaskListPresenter(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        this.service = clientFactory.getTaskService();

        clientFactory.getEventBus().addHandler(
            UpdateProjectListEvent.TYPE,
            new UpdateProjectListEvent.Handler() {
                public void onUpdateProjectList(UpdateProjectListEvent event) {
                    view.setProjectList(event.getProjectList());
                }
            });
        clientFactory.getEventBus().addHandler(
            UpdateTagListEvent.TYPE,
            new UpdateTagListEvent.Handler() {
                public void onUpdateProjectList(UpdateTagListEvent event) {
                    view.setTagList(event.getTagList());
                }
            });
    }
    
    protected List<Task> getTaskList() {
        return taskList;
    }

    /**
     * {@inheritDoc}
     */
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        view = clientFactory.getTaskListView();
        view.setPresenter(this);

        clientFactory.getEventBus().fireEvent(new PrepareTaskListEvent(view));

        panel.setWidget(view);
        loadTaskList();
    }

    /**
     * {@inheritDoc}
     */
    public abstract void loadTaskList();

    /**
     * {@inheritDoc}
     */
    public void createTask(Task task) {
        service.createTask(task, new AsyncCallback<Task>() {
            public void onFailure(Throwable caught) {
                showError(caught);
            }

            public void onSuccess(Task result) {
                showStatus("タスク \"" + result.getName() + "\" を更新しました。");
                afterCreateTask(result);
            }
        });
    }
    
    protected abstract void afterCreateTask(Task result);
    
    protected TaskListView getView() {
        return view;
    }

    /**
     * {@inheritDoc}
     */
    public void updateTask(final Task task) {
        service.updateTask(task, new AsyncCallback<Task>() {
            public void onFailure(Throwable caught) {
                showError(caught);
            }

            public void onSuccess(Task result) {
                showStatus("タスク \"" + result.getName() + "\" を更新しました。");
                afterUpdateTask(task, result);
            }
        });
    }
    
    protected abstract void afterUpdateTask(Task before, Task after);

    /**
     * {@inheritDoc}
     */
    public void deleteTask(final Collection<Task> tasks) {
        List<Key> keys = ModelUtil.tasksToKeyList(tasks);
        service.deleteTask(keys, new AsyncCallback<Void>() {
            public void onFailure(Throwable caught) {
                showError(caught);
            }

            public void onSuccess(Void result) {
                showStatus("タスクを " + tasks.size() + " 件削除しました。");

                taskList.removeAll(tasks);
                view.setTaskList(taskList);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    public void goTo(Place place) {
        clientFactory.getPlaceController().goTo(place);
    }

    /**
     * エラーを表示します。
     * 
     * @param error
     *            表示するエラー。
     */
    protected void showError(Throwable error) {
        showStatus(error.getMessage());
    }

    /**
     * ステータスを表示します。
     * 
     * @param status
     *            表示するステータス。
     */
    protected void showStatus(String status) {
        clientFactory.getEventBus().fireEvent(new ShowStatusEvent(status));
    }
}
