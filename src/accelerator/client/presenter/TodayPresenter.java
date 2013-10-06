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

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import accelerator.client.ClientFactory;
import accelerator.client.place.TodayPlace;
import accelerator.shared.model.Task;

public class TodayPresenter extends TaskListPresenter {
    private final Date today;

    public TodayPresenter(TodayPlace place, ClientFactory clientFactory) {
        super(clientFactory);
        today = new Date();
    }

    @Override
    public void loadTaskList() {
        service.getTodayTaskList(today, new AsyncCallback<List<Task>>() {
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
        if (isTodaysTask(result)) {
            getTaskList().add(result);
            getView().setTaskList(getTaskList());
        }
    }

    private boolean isTodaysTask(Task task) {
        return (task.getDueDate() != null)
            && (task.getDueDate().compareTo(today) <= 0);
    }

    @Override
    protected void afterUpdateTask(Task before, Task after) {
        if (isTodaysTask(after)) {
            int index = getTaskList().indexOf(before);
            getTaskList().set(index, after);
        } else {
            getTaskList().remove(before);
        }
        getView().setTaskList(getTaskList());
    }

}
