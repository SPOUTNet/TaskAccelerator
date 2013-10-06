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

import accelerator.shared.model.Tag;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class TagChangeEvent extends GwtEvent<TagChangeEvent.Handler> {
    public static interface Handler extends EventHandler {
        void onTagChange(TagChangeEvent event);
    }
    
    public static final Type<TagChangeEvent.Handler> TYPE = new Type<TagChangeEvent.Handler>();
    private final Tag tag;
    
    public TagChangeEvent(Tag tag) {
        this.tag = tag;
    }
    
    public Tag getTag() {
        return tag;
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onTagChange(this);
    }
}
