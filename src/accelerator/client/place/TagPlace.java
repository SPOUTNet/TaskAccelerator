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


import accelerator.shared.model.Tag;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class TagPlace extends Place {
    private final Tag tag;
    private final Key tagKey;
    
    public TagPlace(Key tagKey, Tag tag) {
        this.tag = tag;
        this.tagKey = tagKey;
    }
    
    public Tag getTag() {        
        return tag;
    }
    
    public Key getTagKey() {
        return tagKey;
    }
    
    @Prefix("tag")
    public static class Tokenizer implements PlaceTokenizer<TagPlace>{

        public TagPlace getPlace(String token) {
            long id = Long.valueOf(token);
            Key key = KeyFactory.createKey("Tag", id);
            Tag tag = new Tag();
            tag.setKey(key);
            return new TagPlace(key, tag);
        }

        public String getToken(TagPlace place) {
            Key key = place.getTagKey();
            return String.valueOf(key.getId());
        }
        
    }
}
