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
package accelerator.client.place;


import accelerator.shared.model.Project;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class ProjectPlace extends Place {
    private final Project project;
    private final Key projectKey;
    
    public ProjectPlace(Key projectKey, Project project) {
        this.project = project;
        this.projectKey = projectKey;
    }
    
    public Key getProjectKey() {
        return projectKey;
    }
    
    public Project getProject() {
        return project;
    }
    
    @Prefix("project")
    public static class Tokenizer implements PlaceTokenizer<ProjectPlace> {

        public ProjectPlace getPlace(String token) {
            long id = Long.valueOf(token);
            Key key = KeyFactory.createKey("Project", id);
            Project project = new Project();
            project.setKey(key);
            return new ProjectPlace(key, project);
        }

        public String getToken(ProjectPlace place) {
            Key key = place.getProjectKey();
            return String.valueOf(key.getId());
        }
        
    }
}
