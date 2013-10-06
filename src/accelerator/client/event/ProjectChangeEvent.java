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

import accelerator.shared.model.Project;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class ProjectChangeEvent extends GwtEvent<ProjectChangeEvent.Handler> {
    public static interface Handler extends EventHandler {
        void onProjectChange(ProjectChangeEvent event);
    }
    
    public static final Type<ProjectChangeEvent.Handler> TYPE = new Type<ProjectChangeEvent.Handler>();
    
    private final Project project;
    
    public ProjectChangeEvent(Project project) {
        this.project = project;
    }
    
    public Project getProject() {
        return project;
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onProjectChange(this);
    }
}
