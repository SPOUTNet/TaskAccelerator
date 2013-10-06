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
package accelerator.client.activity;


import accelerator.client.ClientFactory;
import accelerator.client.place.InboxPlace;
import accelerator.client.place.ProjectPlace;
import accelerator.client.place.SchedulePlace;
import accelerator.client.place.TagPlace;
import accelerator.client.place.TodayPlace;
import accelerator.client.presenter.InboxPresenter;
import accelerator.client.presenter.ProjectPresenter;
import accelerator.client.presenter.SchedulePresenter;
import accelerator.client.presenter.TagPresenter;
import accelerator.client.presenter.TodayPresenter;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class AppActivityMapper implements ActivityMapper {
    private final ClientFactory clientFactory;
    
    public AppActivityMapper(ClientFactory clientFactory) {
        super();
        this.clientFactory = clientFactory;
    }

    public Activity getActivity(Place place) {
        if (place instanceof InboxPlace) {
            return new InboxPresenter((InboxPlace)place, clientFactory);
        } else if (place instanceof TodayPlace) {
            return new TodayPresenter((TodayPlace)place, clientFactory);
        } else if (place instanceof SchedulePlace) {
            return new SchedulePresenter((SchedulePlace)place, clientFactory);
        } else if (place instanceof ProjectPlace) {
            return new ProjectPresenter((ProjectPlace)place, clientFactory);
        } else if (place instanceof TagPlace) {
            return new TagPresenter((TagPlace)place, clientFactory);
        }
        return null;
    }

}
