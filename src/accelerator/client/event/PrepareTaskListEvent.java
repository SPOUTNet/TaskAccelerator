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
package accelerator.client.event;


import accelerator.client.view.TaskListView;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class PrepareTaskListEvent extends GwtEvent<PrepareTaskListEvent.Handler> {
    public static interface Handler extends EventHandler {
        void onPrepareTaskList(PrepareTaskListEvent event);
    }
    
    private final TaskListView view;
    public static final Type<PrepareTaskListEvent.Handler> TYPE = new Type<PrepareTaskListEvent.Handler>();
    
    public PrepareTaskListEvent(TaskListView view) {
        this.view = view;
    }
    
    public TaskListView getView() {
        return view;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onPrepareTaskList(this);
    }
}
