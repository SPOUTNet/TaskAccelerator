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

import java.util.List;


import accelerator.client.ClientFactory;
import accelerator.client.place.SchedulePlace;
import accelerator.shared.model.Task;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class SchedulePresenter extends TaskListPresenter {

    public SchedulePresenter(SchedulePlace place, ClientFactory clientFactory) {
        super(clientFactory);
    }

    @Override
    public void loadTaskList() {
        service.getScheduledTaskList(new AsyncCallback<List<Task>>() {
            public void onFailure(Throwable caught) {
                showError(caught);
            }

            public void onSuccess(List<Task> result) {
                getTaskList().clear();
                getTaskList().addAll(result);
                getView().setTaskList(getTaskList());
            }
        });
    }

    @Override
    protected void afterCreateTask(Task result) {
        if (result.getDueDate() != null) {
            // 期日が設定してある場合はリストに追加
            List<Task> taskList = getTaskList();
            taskList.add(result);
            getView().setTaskList(taskList);
        }
    }

    @Override
    protected void afterUpdateTask(Task before, Task result) {
        List<Task> taskList = getTaskList();
        if (result.getDueDate() != null) {
            // 期日が設定してある場合は置き換える
            int index = taskList.indexOf(before);
            taskList.set(index, result);
        } else {
            // 期日が設定してない場合は削除
            taskList.remove(before);
        }
        getView().setTaskList(taskList);
    }
}
