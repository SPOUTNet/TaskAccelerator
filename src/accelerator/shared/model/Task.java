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
package accelerator.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.CreationDate;
import org.slim3.datastore.CreationUser;
import org.slim3.datastore.Model;

@Model(schemaVersion = 1)
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(listener = CreationUser.class)
    private User user;
    
    private String name;
    
    private Key project;
    
    private List<Key> tags = new ArrayList<Key>();
    
    private Boolean completed = false;
    
    /**
     * 期日
     */
    private Date dueDate;
    
    /**
     * ノート
     */
    private String note;
    
    @Attribute(listener = CreationDate.class)
    private Date createdDate;
    
    @Attribute(version = true)
    private Long version;

    /**
     * Returns the key.
     *
     * @return the key
     */
    public Key getKey() {
        return key;
    }

    /**
     * Sets the key.
     *
     * @param key
     *            the key
     */
    public void setKey(Key key) {
        this.key = key;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param project the project to set
     */
    public void setProject(Key project) {
        this.project = project;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(List<Key> tags) {
        this.tags = tags;
    }

    /**
     * @return the tags
     */
    public List<Key> getTags() {
        return tags;
    }

    /**
     * @return the project
     */
    public Key getProject() {
        return project;
    }

    /**
     * @param completed the completed to set
     */
    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @return the dueDate
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @return the completed
     */
    public Boolean getCompleted() {
        return completed;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Returns the version.
     *
     * @return the version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Sets the version.
     *
     * @param version
     *            the version
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Task other = (Task) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }
}
